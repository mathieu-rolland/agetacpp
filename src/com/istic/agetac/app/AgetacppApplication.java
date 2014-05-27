package com.istic.agetac.app;

import java.util.List;

import com.istic.agetac.api.model.IUser;
import com.istic.agetac.api.model.IUser.Role;
import com.istic.agetac.model.EnvironnementsStatic;
import com.istic.agetac.model.Intervention;
import com.istic.agetac.model.UserAvailable;
import com.istic.agetac.model.factory.Factory;
import com.istic.agetac.model.serializer.AgetacSerializer;
import com.istic.sit.framework.application.FrameworkApplication;
import com.istic.sit.framework.couch.DataBaseCommunication;
import com.istic.sit.framework.couch.JsonSerializer;

public class AgetacppApplication extends FrameworkApplication
{

    private static IUser user;

    private static Intervention currentIntervention;

    private static List<Intervention> listIntervention;

    private static Role role;

    private static UserAvailable userAvailable;

    private static EnvironnementsStatic environnementsStatic;

    @Override
    public void onCreate()
    {

        super.onCreate();
        JsonSerializer.initSerializer();
        JsonSerializer.initDeserializer();
        AgetacSerializer.init();

        FrameworkApplication.setFactory( new Factory() );
        DataBaseCommunication.BASE_URL = "http://148.60.11.236:5984/motherfucker/";

        // CreationBase.createCleanBase();

    }

    /**
     * @return the user
     */
    public static IUser getUser()
    {
        return user;
    }

    public static Role getRole()
    {
        return role;
    }

    public static void setRole( Role role )
    {
        AgetacppApplication.role = role;
    }

    /**
     * @param user
     * the user to set
     */
    public static void setUser( IUser user )
    {
        AgetacppApplication.user = user;
    }

    public static Intervention getIntervention()
    {
        return currentIntervention;
    }

    public static void setIntervention( Intervention intervention )
    {
        if ( intervention != null )
        {
            intervention.updateDepandencies();
        }
        AgetacppApplication.currentIntervention = intervention;
    }

    public static List<Intervention> getListIntervention()
    {
        return listIntervention;
    }

    public static void setListIntervention( List<Intervention> interventions )
    {
        if ( interventions != null )
        {
            for ( Intervention intervention : interventions )
            {
                intervention.updateDepandencies();
            }
        }
        listIntervention = interventions;
    }

    public static UserAvailable getUserAvailable()
    {
        return userAvailable;
    }

    public static void setUserAvailable( UserAvailable userAvailable )
    {
        AgetacppApplication.userAvailable = userAvailable;
    }

    /**
     * @return the environnementsStatic
     */
    public static EnvironnementsStatic getEnvironnementsStatic()
    {
        return environnementsStatic;
    }

    /**
     * @param environnementsStatic
     * the environnementsStatic to set
     */
    public static void setEnvironnementsStatic( EnvironnementsStatic environnementsStatic )
    {
        AgetacppApplication.environnementsStatic = environnementsStatic;
    }

}
