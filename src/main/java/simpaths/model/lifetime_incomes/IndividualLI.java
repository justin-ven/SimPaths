package simpaths.model.lifetime_incomes;

import java.util.*;

import java.util.random.RandomGenerator;

public class IndividualLI {


    /**
     * ATTRIBUTES
     */

    LifetimeIncomes lifetimeIncomes;
    BirthCohortLI birthCohort;
    private List<AnnualIncomeLI> incomes = new ArrayList<>();
    private double alpha;
    private double sdEta;
    private RandomGenerator generator;


    /**
     * CONSTRUCTOR
     */

    public IndividualLI() {}
    public IndividualLI(BirthCohortLI birthCohort, int endAge, long seed) {
        lifetimeIncomes = birthCohort.getLifetimeIncomes();
        this.birthCohort = birthCohort;
        generator = new Random(seed);
        double rnd = generator.nextDouble();
        alpha = lifetimeIncomes.getAlphaSim(rnd);
        sdEta = Math.exp((lifetimeIncomes.getSdEtaIntercept() + lifetimeIncomes.getSdEtaSlope() * alpha)/2.0);
        populateIncomes(endAge);
    }


    /**
     * GETTERS AND SETTERS
     */

    AnnualIncomeLI getAnnualIncome(int age) {
        return incomes.get(age);
    }
    BirthCohortLI getBirthCohort() {
        return birthCohort;
    }
    double getAlpha() {
        return alpha;
    }


    /**
     * WORKER METHODS
     */

    private void populateIncomes(int endAge) {

        AnnualIncomeLI incomeTm1 = null, income;
        for (int aa=0; aa <= endAge; aa++) {

            income = addAnnualIncome(incomeTm1, aa);
            incomes.add(income);
            incomeTm1 = income;
        }
    }

    public AnnualIncomeLI addAnnualIncome(AnnualIncomeLI incomeTm1, int age) {

        double rnd = generator.nextDouble();
        double etaStar = lifetimeIncomes.getEtaStar(rnd);
        double etaHere = etaStar * sdEta;
        return new AnnualIncomeLI(this, age, etaHere, incomeTm1);
    }
}
