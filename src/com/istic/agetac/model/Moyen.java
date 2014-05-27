package com.istic.agetac.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.graphics.Color;
import android.os.Parcel;
import android.util.Log;

import com.istic.agetac.api.model.IMoyen;
import com.istic.agetac.app.AgetacppApplication;
import com.istic.sit.framework.api.model.IPosition;
import com.istic.sit.framework.api.model.IProperty;
import com.istic.sit.framework.api.model.IRepresentation;
import com.istic.sit.framework.model.Entity;
import com.istic.sit.framework.model.Property;

/**
 * Classe Moyen : Modéle qui représente un moyen (i.e. Véhicule)
 * 
 * @author Anthony LE MEE - 10003134
 */
public class Moyen extends Entity implements IMoyen
{

    public static final SimpleDateFormat FORMATER = new SimpleDateFormat( "ddMM '-' hhmm" ); // ("ddMM '-' hhmm");

    /** Constante string which defines name of property of type of moyen */
    public static final String NAME_PROPERTY_TYPE = "moyen_type";

    /** Constante string which defines name of property of moyen demand hour */
    public static final String NAME_PROPERTY_HOUR_DEMAND = "moyen_hour_demand";

    /** Constante string which defines name of property of moyen engagement hour */
    public static final String NAME_PROPERTY_HOUR_ENGAGEMENT = "moyen_hour_engagement";

    /** Constante string which defines name of property of moyen arrival hour */
    public static final String NAME_PROPERTY_HOUR_ARRIVAL = "moyen_hour_arrival";

    /** Constante string which defines name of property of moyen free hour */
    public static final String NAME_PROPERTY_HOUR_FREE = "moyen_hour_free";

    /** Constante string which defines name of property of moyen secteur hour */
    public static final String NAME_PROPERTY_SECTEUR = "moyen_secteur";

    public static final String NAME_PROPERTY_INGROUP = "is_in_group";

    private transient Intervention intervention;

    private List<IMoyen> moyens;

    /**
     * Constructeur de la classe Moyen pour créer un MOYEN
     * 
     * @param typeValue
     * : Type of moyen
     */
    public Moyen( TypeMoyen typeValue, Intervention intervention )
    {
        super();
        this.setRepresentationKO( typeValue.getRepresentationKO() );
        this.setRepresentationOK( typeValue.getRepresentationOK() );
        IProperty typeProperty = creatProperty( NAME_PROPERTY_TYPE, typeValue.toString() );
        IProperty hDemandProperty = creatProperty( NAME_PROPERTY_HOUR_DEMAND, FORMATER.format( new Date() ) );
        IProperty hEngagementProperty = creatProperty( NAME_PROPERTY_HOUR_ENGAGEMENT, null );
        IProperty hArrivalProperty = creatProperty( NAME_PROPERTY_HOUR_ARRIVAL, null );
        IProperty hFreeProperty = creatProperty( NAME_PROPERTY_HOUR_FREE, null );
        IProperty secteurProperty = creatProperty( NAME_PROPERTY_SECTEUR, null );
        IProperty ingroupProperty = creatProperty( NAME_PROPERTY_INGROUP, "0" );
        super.addPropriete( typeProperty );
        super.addPropriete( hDemandProperty );
        super.addPropriete( hEngagementProperty );
        super.addPropriete( hArrivalProperty );
        super.addPropriete( hFreeProperty );
        super.addPropriete( secteurProperty );
        this.intervention = intervention;
        super.addPropriete( ingroupProperty );

        moyens = new ArrayList<IMoyen>();
    }

    /**
     * Constructeur de la classe Moyen pour créer un MOYEN
     * 
     * @param typeValue
     * : Type of moyen
     * @param position
     * : position of moyen
     */
    public Moyen( TypeMoyen typeValue, Intervention intervention, IPosition position )
    {

        super( position );

        IProperty typeProperty = creatProperty( NAME_PROPERTY_TYPE, typeValue.toString() );
        IProperty hDemandProperty = creatProperty( NAME_PROPERTY_HOUR_DEMAND, FORMATER.format( new Date() ) );
        IProperty hEngagementProperty = creatProperty( NAME_PROPERTY_HOUR_ENGAGEMENT, null );
        IProperty hArrivalProperty = creatProperty( NAME_PROPERTY_HOUR_ARRIVAL, null );
        IProperty hFreeProperty = creatProperty( NAME_PROPERTY_HOUR_FREE, null );
        IProperty secteurProperty = creatProperty( NAME_PROPERTY_SECTEUR, null );

        IProperty ingroupProperty = creatProperty( NAME_PROPERTY_INGROUP, "0" );

        super.addPropriete( typeProperty );
        super.addPropriete( hDemandProperty );
        super.addPropriete( hEngagementProperty );
        super.addPropriete( hArrivalProperty );
        super.addPropriete( hFreeProperty );
        super.addPropriete( secteurProperty );
        super.addPropriete( ingroupProperty );
        moyens = new ArrayList<IMoyen>();
        
    }

    /**
     * Constructeur pour créer un GROUPE
     * @param liste - Sa liste de moyen
     * @param intervention - L'intervention à laquelle il est assigné
     */
    public Moyen(List<IMoyen> liste, Intervention intervention)
    {
        
    	super();

        IProperty typeProperty = creatProperty( NAME_PROPERTY_TYPE, null);
        IProperty hDemandProperty = creatProperty( NAME_PROPERTY_HOUR_DEMAND, FORMATER.format( new Date() ) );
        IProperty hEngagementProperty = creatProperty( NAME_PROPERTY_HOUR_ENGAGEMENT, null );
        IProperty hArrivalProperty = creatProperty( NAME_PROPERTY_HOUR_ARRIVAL, null );
        IProperty hFreeProperty = creatProperty( NAME_PROPERTY_HOUR_FREE, null );
        IProperty secteurProperty = creatProperty( NAME_PROPERTY_SECTEUR, null );

        IProperty ingroupProperty = creatProperty( NAME_PROPERTY_INGROUP, "0" );

        super.addPropriete( typeProperty );
        super.addPropriete( hDemandProperty );
        super.addPropriete( hEngagementProperty );
        super.addPropriete( hArrivalProperty );
        super.addPropriete( hFreeProperty );
        super.addPropriete( secteurProperty );
        super.addPropriete( ingroupProperty );
        addMoyens( liste );
        super.setGroup(true);
        
        // On set une position par défaut si c'est possible
        /*FIXME NullPointer Exception 
        	if (liste.size() > 0 && liste.get(0).getSecteur() != null && liste.get(0).getSecteur().getPosition() != null) {
        	for (IMoyen moyen : liste) {
        		((Entity)moyen).setPosition(liste.get(0).getSecteur().getPosition());
        	}
        	this.setPosition(liste.get(0).getSecteur().getPosition());
        }*/
    
    }

    public void addMoyens( List<IMoyen> liste )
    {
        moyens = new ArrayList<IMoyen>();

        for ( IMoyen iMoyen : liste )
        {
            iMoyen.setIsInGroup( true );
        }
        intervention.getMoyens().removeAll(liste);
        moyens.addAll( liste );
    }

    public boolean isInGroup()
    {
        if ( super.getProperty( NAME_PROPERTY_INGROUP ).getValeur().equals( "0" ) )
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public void setIsInGroup( boolean bool )
    {
        String ingroup = ( bool ) ? "1" : "0";
        super.getProperty( NAME_PROPERTY_INGROUP ).setValeur( ingroup );
        Log.d( "Antho", ingroup + " pour : " + this.toString() );
    }

    /**
     * Constructeur de la classe Moyen
     * 
     * @param source
     * : moyen
     */
    public Moyen( Parcel source )
    {
        super( source );
    }

    public TypeMoyen getType()
    {

        if ( super.getProperty( NAME_PROPERTY_TYPE ).getValeur().equals( TypeMoyen.VSAV.toString() ) )
        {
            return TypeMoyen.VSAV;
        }
        else if ( super.getProperty( NAME_PROPERTY_TYPE ).getValeur().equals( TypeMoyen.FPT.toString() ) )
        {
            return TypeMoyen.FPT;
        }
        else if ( super.getProperty( NAME_PROPERTY_TYPE ).getValeur().equals( TypeMoyen.CCFM.toString() ) )
        {
            return TypeMoyen.CCGC;
        }
        else if ( super.getProperty( NAME_PROPERTY_TYPE ).getValeur().equals( TypeMoyen.VAR.toString() ) )
        {
            return TypeMoyen.VAR;
        }
        else if ( super.getProperty( NAME_PROPERTY_TYPE ).getValeur().equals( TypeMoyen.VLCC.toString() ) )
        {
            return TypeMoyen.VLCC;
        }
        else if ( super.getProperty( NAME_PROPERTY_TYPE ).getValeur().equals( TypeMoyen.VLS.toString() ) )
        {
            return TypeMoyen.VLS;
        }
        else if ( super.getProperty( NAME_PROPERTY_TYPE ).getValeur().equals( TypeMoyen.VSR.toString() ) )
        {
            return TypeMoyen.VSR;
        }
        else
        {
            return null; // FIXME add throw WARNING
        }

    }

    public Date getHDemande()
    {
        try
        {
            return FORMATER.parse( super.getProperty( NAME_PROPERTY_HOUR_DEMAND ).getValeur() );
        }
        catch ( Exception e )
        {
            return null;
        }
    }

    public Date getHArrival()
    {
        try
        {
            return FORMATER.parse( super.getProperty( NAME_PROPERTY_HOUR_ARRIVAL ).getValeur() );
        }
        catch ( Exception e )
        {
            return null;
        }
    }

    public Date getHEngagement()
    {
        try
        {
            return FORMATER.parse( super.getProperty( NAME_PROPERTY_HOUR_ENGAGEMENT ).getValeur() );
        }
        catch ( Exception e )
        {
            return null;
        }
    }

    public Date getHFree()
    {
        try
        {
            return FORMATER.parse( super.getProperty( NAME_PROPERTY_HOUR_FREE ).getValeur() );
        }
        catch ( Exception e )
        {
            return null;
        }
    }

    public Secteur getSecteur()
    {
        int i = 0;
        List<Secteur> secteurs = intervention.getSecteurs();
        String name = super.getProperty( NAME_PROPERTY_SECTEUR ).getValeur();
        Secteur secteur = null;

        while ( i < secteurs.size() && secteur == null )
        {
            if ( secteurs.get( i ).getLibelle().equals(name) )
            {
                secteur = secteurs.get( i );
            }
            i++;
        }

        return secteur;
    } // method

    public void setType( String valeur )
    {
        super.getProperty( NAME_PROPERTY_TYPE ).setValeur( valeur );
    } // method

    public void setHDemande( Date valeur )
    {
        super.getProperty( NAME_PROPERTY_HOUR_DEMAND ).setValeur( FORMATER.format( valeur ) );
    } // method

    public void setHArrival( Date valeur )
    {
        super.getProperty( NAME_PROPERTY_HOUR_ARRIVAL ).setValeur( FORMATER.format( valeur ) );
        super.setOk( true );
    } // method

    public void setHEngagement( Date dateEngage )
    {
        super.getProperty( NAME_PROPERTY_HOUR_ENGAGEMENT ).setValeur( FORMATER.format( dateEngage ) );
    } // method

    public void setHFree( Date valeur )
    {
        super.getProperty( NAME_PROPERTY_HOUR_FREE ).setValeur( FORMATER.format( valeur ) );
    } // method

    /**
     * Method which return secteur of moyen
     * 
     * @return String : secteur of moyen
     */
    public void setSecteur( String valeur )
    {
        if ( valeur == null || valeur.equals( "SLL" ) )
        {
            super.setColor( Color.WHITE );
        }
        else if ( valeur.equals( "INC" ) )
        {
            super.setColor( Color.RED );
        }
        else if ( valeur.equals( "SAP" ) )
        {
            super.setColor( Color.YELLOW );
        }
        else if ( valeur.equals( "ALIM" ) )
        {
            super.setColor( Color.GREEN );
        }
        else if ( valeur.equals( "CRM" ) )
        {
            super.setColor( Color.MAGENTA );
        }
        else
        {
            super.setColor( Color.WHITE );
        }
        super.getProperty( NAME_PROPERTY_SECTEUR ).setValeur( valeur );
    } // method

    /**
     * Method which return secteur of moyen
     * 
     * @return String : secteur of moyen
     */
    public void setSecteur( Secteur secteur )
    {
        super.setColor( secteur.getColor() );
        super.getProperty( NAME_PROPERTY_SECTEUR ).setValeur( secteur.getName() );
    } // method

    public IProperty creatProperty( String name, String value )
    {
        IProperty property = new Property();
        property.setNom( name );
        property.setValeur( value );
        return property;
    }

    @Override
    public void save()
    {
        AgetacppApplication.getIntervention().addMoyen( this );
        try
        {
        	AgetacppApplication.getIntervention().addHistorique( new Action( AgetacppApplication.getUser().getName(), new Date(), "Modification du moyen " + this.getLibelle() ) );
        }
        catch ( Exception e )
        {
            Log.e( "HISTORIQUE", "Impossible de récupérer le user deAgetacApplication" );
        }
        AgetacppApplication.getIntervention().save();
    }

    @Override
    public void delete()
    {
        intervention.delete( this );
        try
        {
        	AgetacppApplication.getIntervention().addHistorique( new Action( AgetacppApplication.getUser().getName(), new Date(), "Suppression du moyen " + this.getLibelle() ) );
        }
        catch ( Exception e )
        {
            Log.e( "HISTORIQUE", "Impossible de récupérer le user deAgetacApplication" );
        }

    }

    @Override
    public int describeContents()
    {
        return super.CONTENTS_FILE_DESCRIPTOR;
    }

    @Override
    public void writeToParcel( Parcel dest, int flags )
    {
        // TODO
    }

    @Override
    public String getId()
    {
        return super.getId();
    }

    @Override
    public void setId( String id )
    {
        super.setId( id );
    }

    @Override
    public String getLibelle()
    {
        return super.getLibelle();
    }

    @Override
    public void setLibelle( String label )
    {
        super.setLibelle( label );
    }

    @Override
    public void addPropriete( IProperty property )
    {
        super.addPropriete( property );
    }

    @Override
    public IRepresentation getRepresentationOK()
    {
        return super.getRepresentationOK();
    }

    @Override
    public void setRepresentationOK( IRepresentation representation )
    {
        super.setRepresentationOK( representation );
    }

    @Override
    public IRepresentation getRepresentationKO()
    {
        return super.getRepresentationKO();
    }

    @Override
    public void setRepresentationKO( IRepresentation representation )
    {
        super.setRepresentationKO( representation );
    }

    @Override
    public IRepresentation getRepresentation()
    {
        if ( this.getType() == null )
        {
            if ( isOk() )
            {
                return super.getRepresentationOK();
            }
            else
            {
                return super.getRepresentationOK();
            }
        }
        if ( isOk() )
        {
            return this.getRepresentationOK();
        }
        else
        {
            return this.getRepresentationKO();
        }
    }

    @Override
    public IPosition getPosition()
    {
        return super.getPosition();
    }

    @Override
    public void setPosition( IPosition positon )
    {
        super.setPosition( positon );
    }

    @Override
    public boolean isFrozen()
    {
        return super.isFrozen();
    }

    @Override
    public boolean isOnMap()
    {
        return super.isOnMap();
    }

    @Override
    public void setOnMap( boolean onMap )
    {
        super.setOnMap( onMap );
    }

    @Override
    public boolean isOk()
    {
        return super.isOk();
    }

    @Override
    public void setOk( boolean ok )
    {
        super.setOk( ok );
    }

    @Override
    public void setFrozen( boolean frozen )
    {
        super.setFrozen( frozen );
    }

    @Override
    public boolean isGroup()
    {
        return super.isGroup();
    }

    public Intervention getIntervention()
    {
        return intervention;
    }

    public void setIntervention( Intervention intervention )
    {
        this.intervention = intervention;
    }

    @Override
    public String toString()
    {
        return this.getLibelle();
    }

    public boolean equals( Moyen moyen )
    {
        return this.getId().equals( moyen.getId() );
    }
    @Override
    public List<IMoyen> getListMoyen()
    {
        return moyens;
    }

    @Override
    public void setListMoyen( List<IMoyen> liste )
    {
        moyens = liste;
    }

    @Override
    public void addMoyen( IMoyen moyen )
    {
        moyen.setIsInGroup( true );
        intervention.getMoyens().remove(moyen);
        moyens.add( moyen );
    }

    @Override
    public void deleteAllMoyen()
    {
        if ( isGroup() && moyens != null )
        {
            for ( IMoyen moyen : moyens )
            {
                moyen.setIsInGroup( false );
                intervention.addMoyen((Moyen)moyen);
            }

            moyens.clear();
        }
    }

    @Override
    public void deleteMoyen( IMoyen moyen )
    {
        if ( !isGroup() )
            return;

        if ( !moyen.isGroup() && moyen.getListMoyen() != null )
        {
            moyen.setIsInGroup( false );
            moyens.remove( moyen );
        }
    }


}
