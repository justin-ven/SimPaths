package simpaths.model.lifetime_incomes;

import microsim.statistics.regression.LinearRegression;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;
import simpaths.data.Parameters;
import simpaths.model.BenefitUnit;
import simpaths.model.Household;
import simpaths.model.Person;
import simpaths.model.enums.Gender;

import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

record Cohort(int birthYear, Gender gender) {}

public class LifetimeIncomes {


    /**
     * ATTRIBUTES
     */

    private int startBirthYear;
    private int endBirthYear;
    private int endAge;
    private HashMap<Cohort, BirthCohortLI> cohorts = new HashMap<>();     // hashmap of birth cohorts
    protected List<Double> alphaSim;                                    // estimated normalised population of individual fixed effects
    protected List<Double> etaStar;                                     // estimated normalised population of individual residuals
    private double beta;                                                // estimated regression to the mean
    private double gamma1;                                              // estimated auto-correlation of residual (epsilon)
    private double sdEtaIntercept;                                      // estimated hetroscedasticity of eta with alphaSim - intercept
    private double sdEtaSlope;                                          // estimated hetroscedasticity of eta with alphaSim - slope


    /**
     * CONSTRUCTOR
     */
    public LifetimeIncomes() {
    }

    public LifetimeIncomes(Logger log, Integer startYear, Integer startBirthYear, Integer endBirthYear,
                           Integer endAge, Integer simCohortSize, long seed) {

        if (startBirthYear==null)
            throw new RuntimeException("startBirthYear is null");
        if (endBirthYear==null)
            throw new RuntimeException("endBirthYear is null");
        if (endAge==null)
            throw new RuntimeException("endAge is null");

        String msg = "Initialising lifetime income projections";
        log.info(msg);
        System.out.println(msg);

        msg = "Loading parameters";
        log.info(msg);
        System.out.println(msg);
        LinearRegression reg = Parameters.getRegLifetimeIncomeLI2a();
        beta = reg.getCoefficient("beta");
        gamma1 = reg.getCoefficient("gamma1");
        reg = Parameters.getRegLifetimeIncomeLI2b();
        sdEtaIntercept = reg.getCoefficient("Constant");
        sdEtaSlope = reg.getCoefficient("alpha_sim");
        this.startBirthYear = startBirthYear;
        this.endBirthYear = endBirthYear;
        this.endAge = endAge;
        String filePath, weight, variable, control;

        msg = "Loading data for resampling lifetime income innovations";
        log.info(msg);
        System.out.println(msg);

        filePath = Parameters.INPUT_DIRECTORY + File.separator + "ltinc_fixedeffects.csv";
        weight = "wgt_indiv";
        variable = "alpha_sim";
        control = null;
        alphaSim = loadEstimatedResiduals(filePath, weight, variable, control);

        filePath = Parameters.INPUT_DIRECTORY + File.separator + "ltinc_whitenoise.csv";
        weight = "wgt";
        variable = "eta_hat";
        control = "alpha_sim";
        etaStar = loadEstimatedResiduals(filePath, weight, variable, control);

        msg = "Generate lifetime incomes by cohort";
        log.info(msg);
        System.out.println(msg);
        int jj = 0;
        for (int birthYear = startBirthYear; birthYear <= endBirthYear; birthYear++) {
            // loop over birth years

            msg = "Projecting lifetime incomes for birth year " + birthYear;
            log.info(msg);
            System.out.println(msg);
            for (Gender gender : Gender.values()) {
                // loop over gender

                Cohort cohort = new Cohort(birthYear, gender);
                BirthCohortLI birthCohort = new BirthCohortLI(this, birthYear, gender);
                cohorts.put(cohort, birthCohort);
//                IntStream.range(0, simCohortSize).parallel().forEach(ii -> {
                for (int ii=0; ii < simCohortSize; ii++) {
                    // loop over new individuals

                    long seedHere = seed + (long)(endAge * jj + ii);
                    birthCohort.addIndividual(endAge, seedHere);
                }
                jj++;
//                });
                birthCohort.sortIndividualsAtYear(startYear);
            }
        }

        msg = "Completed lifetime income projections";
        log.info(msg);
        System.out.println(msg);
    }


    /**
     * GETTERS AND SETTERS
     */

    protected double getSdEtaIntercept(){
        return sdEtaIntercept;
    }
    protected double getSdEtaSlope(){
        return sdEtaSlope;
    }
    protected double getBeta(){
        return beta;
    }
    protected double getGamma1(){
        return gamma1;
    }
    protected double getAlphaSim(double rnd) {
        return alphaSim.get((int) (rnd * (alphaSim.size() - 1)));
    }
    protected double getEtaStar(double rnd) {
        return etaStar.get((int) (rnd * (etaStar.size() - 1)));
    }
    protected int getEndAge() {return endAge;}
    protected int getStartBirthYear() {return startBirthYear;}
    protected int getEndBirthYear() {return endBirthYear;}


    /**
     * WORKER METHODS
     */

    private List<Double> loadEstimatedResiduals(String filePath, String weight, String variable, String control) {

        // load in simulation fixed effects (alphaSim)
        List<Double> vector = new ArrayList<>();
        if (!validateFileExists(filePath))
            throw new RuntimeException("file not found: " + filePath);

        try (
             Reader reader = new FileReader(filePath);
             CSVParser parser = CSVFormat.DEFAULT
                     .builder()
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .build()
                     .parse(reader)
        ) {
            for (CSVRecord record : parser) {

                double wgt = Double.parseDouble(record.get(weight));
                double vrb = Double.parseDouble(record.get(variable));
                if (control!=null) {

                    double cnt = Double.parseDouble(record.get(control));
                    double sd = Math.exp((sdEtaIntercept + sdEtaSlope * cnt)/2.0);
                    vrb = vrb / sd;
                }
                int reps = (int) Math.round(wgt * 10.0);
                for (int ii = 0; ii < reps; ii++) {

                    vector.add(vrb);
                }
            }
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
        return vector;
    }

    /**
     * METHOD TO CHECK THAT FILE EXISTS
     * @param file_path full path of file to delete if it exists
     * @return boolean true if file exists
     */
    private static boolean validateFileExists(String file_path) {
        File file = new File(file_path);
        return file.exists();
    }

    public void matchDonorProfiles(List<Household> households) {

        if (ltIncomeNeeded(households)) {
            // require imputation of income histories
            System.out.println("Imputing income histories for simulated population");
            IntStream.range(0, households.size()).sorted().parallel().forEach(ii -> {
                //for (int ii=0; ii < households.size(); ii++) {

                // consider each household in initialisation set
                Household household = households.get(ii);

                // identify target equivalised household income
                double disposableIncomePerAnnum = 0.0;
                double equivalenceScale = 0.0;
                boolean firstAdult = true;
                for (BenefitUnit benefitUnit : household.getBenefitUnits()) {
                    disposableIncomePerAnnum += benefitUnit.getDisposableIncomeMonthly() * 12.0;
                    for (Person pp : benefitUnit.getMembers()) {
                        if (pp.getDemAge() > 13) {
                            if (firstAdult) {
                                equivalenceScale += 1.0;
                                firstAdult = false;
                            } else {
                                equivalenceScale += 0.5;
                            }
                        } else {
                            equivalenceScale += 0.3;
                        }
                    }
                }
                double targetIncome = disposableIncomePerAnnum / equivalenceScale;

                for (BenefitUnit benefitUnit : household.getBenefitUnits()) {
                    // loop through each benefit unit

                    for (Person person : benefitUnit.getMembers()) {
                        // loop through each person

                        if (person.getDemAge()==0) {
                            person.setLtIncome(endAge);
                        }
                        else {

                            // match birth cohort (birth year and gender)
                            List<IndividualLI> individuals = getLtIncomeDonors(person);
                            if (individuals.isEmpty())
                                throw new IllegalArgumentException("No individuals found for person " + person.getId());

                            // match income
                            int lwr = 0, upr = individuals.size() - 1;
                            double lwrValue = individuals.get(lwr).getAnnualIncome(year).getValue() - targetIncome;
                            if (lwrValue > 0.0) {
                                // lower bound

                                person.setLifetimeIncomeDonor(individuals.get(lwr));
                            } else {
                                double uprValue = individuals.get(upr).getAnnualIncome(year).getValue() - targetIncome;
                                if (uprValue < 0.0) {
                                    // upper bound

                                    person.setLifetimeIncomeDonor(individuals.get(upr));
                                } else {
                                    // find bounded value

                                    while (lwr < upr - 1) {
                                        int tstIndex = (upr + lwr) / 2;
                                        double tstValue = individuals.get(tstIndex).getAnnualIncome(year).getValue() - targetIncome;
                                        if (tstValue < 0.0) {
                                            lwr = tstIndex;
                                            lwrValue = tstValue;
                                        } else {
                                            upr = tstIndex;
                                            uprValue = tstValue;
                                        }
                                    }
                                    if (uprValue < -lwrValue) {
                                        person.setLifetimeIncomeDonor(individuals.get(upr));
                                    } else {
                                        person.setLifetimeIncomeDonor(individuals.get(lwr));
                                    }
                                }
                            }
                            person.setLtIncome(endAge);
                        }
                    }
                }
            });
            //}
            System.out.println("Completed imputing income histories for simulated population");
        }
    }

    private List<IndividualLI> getLtIncomeDonors(Person person) {
        int birthYear = year - Math.min(person.getDemAge(),endAge);
        Gender gender = person.getDemMaleFlag();
        BirthCohortLI targetCohort = null;
        for (BirthCohortLI cohort : cohorts) {

            if (cohort.getBirthYear() == birthYear && cohort.getGender() == gender) {
                targetCohort = cohort;
                break;
            }
        }
        if (targetCohort == null)
            throw new IllegalArgumentException("No cohort found for birth year " + birthYear + " and gender " + gender);
        List<IndividualLI> individuals = targetCohort.getSortedIndividuals();
        return individuals;
    }

    private boolean ltIncomeNeeded(List<Household> households) {

        boolean ltIncomeNeeded = false;
        if (!households.isEmpty()) {

            Iterator<Household> iteratorHousehold = households.iterator();
            Household household = iteratorHousehold.next();
            Set<BenefitUnit> benefitUnits = household.getBenefitUnits();
            if (!benefitUnits.isEmpty()) {

                Iterator<BenefitUnit> iteratorBenefitUnit = benefitUnits.iterator();
                BenefitUnit benefitUnit = iteratorBenefitUnit.next();
                Set<Person> persons = benefitUnit.getMembers();
                if (!persons.isEmpty()) {

                    Iterator<Person> iteratorPerson = persons.iterator();
                    Person person = iteratorPerson.next();
                    ltIncomeNeeded = (person.getLifetimeIncomeDonor() == null);
                }
            }
        }
        return ltIncomeNeeded;
    }
}
