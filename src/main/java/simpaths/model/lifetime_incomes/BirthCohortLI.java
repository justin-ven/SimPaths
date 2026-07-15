package simpaths.model.lifetime_incomes;

import java.util.*;

import simpaths.data.Parameters;
import simpaths.model.enums.Gender;

public class BirthCohortLI extends LifetimeIncomes {


    /**
     * ATTRIBUTES
     */

    private LifetimeIncomes lifetimeIncomes;
    private Integer birthYear;
    private Gender gender;
    List<IndividualLI> individuals;


    /**
     * CONSTRUCTOR
     */
    public BirthCohortLI() {}
    public BirthCohortLI(LifetimeIncomes lifetimeIncomes, Integer birthYear, Gender gender) {
        this.lifetimeIncomes = lifetimeIncomes;
        this.birthYear = birthYear;
        this.gender = gender;
        individuals = new ArrayList<>();
    }


    /**
     * GETTERS AND SETTERS
     */

    protected LifetimeIncomes getLifetimeIncomes() {
        return lifetimeIncomes;
    }
    protected int getBirthYear() {
        return birthYear;
    }
    protected Gender getGender() {
        return gender;
    }


    /**
     * WORKER METHODS
     */

    public void addIndividual(int endAge, long seed) {
        IndividualLI individual = new IndividualLI(this, endAge, seed);
        individuals.add(individual);
    }

    public void sortIndividualsAtYear(Integer startYear) {

        if (!Parameters.checkFinite(startYear))
                throw new IllegalArgumentException("startYear must be finite");

        int age = Math.min(startYear - birthYear, lifetimeIncomes.getEndAge());
        if (age >= 0) {

            IndividualLIComparator comparator = new IndividualLIComparator(age);
            individuals.sort(comparator);
        }
    }
}
