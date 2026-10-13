package simpaths.model.lifetime_incomes;

import simpaths.model.Person;

public class ImputedIncome {

    private long id;
    private int age;
    private int year;
    private double income;
    private Double observedIncome;

    public ImputedIncome() {}
    public ImputedIncome(Person person, int age) {
        this.id = person.getKey().getId();
        this.age = age;
        this.year = person.getYear() - person.getDemAge() + age;
        this.income = person.getImputedIncome(age);
        if (person.getDemAge() == age) {

            double dispIncomePerYear = person.getBenefitUnit().getYDispMonth() * 12.0;
            double equivScale = person.getBenefitUnit().getEquivalisedWeight();
            this.observedIncome = dispIncomePerYear / equivScale;
        }
        else {
            this.observedIncome = null;
        }
    }

    public Double getObservedIncome() {
        return observedIncome;
    }

    public void setObservedIncome(Double observedIncome) {
        this.observedIncome = observedIncome;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
