package simpaths.model.lifetime_incomes;

import java.util.Comparator;

public class IndividualLIComparator implements Comparator<IndividualLI> {

    int age;

    @Override
    public int compare(IndividualLI firstIndividual, IndividualLI secondIndividual) {
        return Double.compare(firstIndividual.getAnnualIncome(age).getIncome(),
                secondIndividual.getAnnualIncome(age).getIncome());
    }

    public IndividualLIComparator(int age) {
        this.age = age;
    }
}
