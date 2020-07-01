package gateways;

import entities.Restrictions;

public class InMemoryRestrictionsGateway implements RestrictionsGateway{

    private Restrictions currentRestriction;

    public InMemoryRestrictionsGateway(Restrictions restrictions){
        currentRestriction = restrictions;
    }

    @Override
    public Restrictions getRestrictions() {
        return currentRestriction;
    }

    @Override
    public boolean updateRestrictions(Restrictions restrictions) {
        this.currentRestriction = restrictions;
        return true;
    }

}
