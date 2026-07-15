package simpaths.model.lifetime_incomes;

public class AnnualIncomeLI extends IndividualLI {


    /**
     * ATTRIBUTES
     */

    LifetimeIncomes lifetimeIncomes;
    BirthCohortLI birthCohort;
    IndividualLI individual;
    private int age;
    private double eta;
    private double zz;
    private double epsilon;
    private double income;


    /**
     * CONSTRUCTOR
     */

    public AnnualIncomeLI() {}
    public AnnualIncomeLI(IndividualLI individual, int age, double eta, AnnualIncomeLI incomeTm1) {
        this.individual = individual;
        this.birthCohort = individual.getBirthCohort();
        this.lifetimeIncomes = birthCohort.getLifetimeIncomes();
        this.age = age;
        this.eta = eta;
        epsilon = projectEpsilon(incomeTm1);
        zz = projectZZ(incomeTm1);
        income = projectIncome();
    }


    /**
     * GETTERS AND SETTERS
     */

    public double getIncome() {
        return income;
    }


    /**
     * WORKER METHODS
     */

    private double projectEpsilon(AnnualIncomeLI incomeTm1) {

        double epsilonTm1 = (age > 0) ? incomeTm1.epsilon : 0.0;
        return individual.getAlpha() + lifetimeIncomes.getGamma1() * epsilonTm1 + eta;
    }

    private double projectZZ(AnnualIncomeLI incomeTm1) {

        double zzTm1 = (age > 0) ? incomeTm1.zz : 0.0;
        return lifetimeIncomes.getBeta() * zzTm1 + eta;
    }

    private double projectIncome() {

        GMIncome gmIncome = new GMIncome(age, birthCohort.getBirthYear() + age, birthCohort.getGender());
        return Math.exp(zz) * gmIncome.getIncome();
    }
}
