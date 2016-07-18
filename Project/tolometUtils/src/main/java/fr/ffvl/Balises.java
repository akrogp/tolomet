//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2016.07.18 a las 05:14:19 PM CEST 
//


package fr.ffvl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="balise" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="idBalise" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="nom" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="departement">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="coord">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="lat" type="{http://www.w3.org/2001/XMLSchema}double" />
 *                           &lt;attribute name="lon" type="{http://www.w3.org/2001/XMLSchema}double" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="altitude">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="remarques" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="url">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="url_histo">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="active" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="forKyte" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "balise"
})
@XmlRootElement(name = "balises")
public class Balises {

    @XmlElement(required = true)
    protected List<Balises.Balise> balise;

    /**
     * Gets the value of the balise property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the balise property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBalise().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Balises.Balise }
     * 
     * 
     */
    public List<Balises.Balise> getBalise() {
        if (balise == null) {
            balise = new ArrayList<Balises.Balise>();
        }
        return this.balise;
    }


    /**
     * <p>Clase Java para anonymous complex type.
     * 
     * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="idBalise" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="nom" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="departement">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}int" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="coord">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;attribute name="lat" type="{http://www.w3.org/2001/XMLSchema}double" />
     *                 &lt;attribute name="lon" type="{http://www.w3.org/2001/XMLSchema}double" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="altitude">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}int" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="remarques" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="url">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="url_histo">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="active" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="forKyte" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "idBalise",
        "nom",
        "departement",
        "coord",
        "altitude",
        "description",
        "remarques",
        "url",
        "urlHisto",
        "active",
        "forKyte"
    })
    public static class Balise {

        protected int idBalise;
        @XmlElement(required = true)
        protected String nom;
        @XmlElement(required = true)
        protected Balises.Balise.Departement departement;
        @XmlElement(required = true)
        protected Balises.Balise.Coord coord;
        @XmlElement(required = true)
        protected Balises.Balise.Altitude altitude;
        @XmlElement(required = true)
        protected String description;
        @XmlElement(required = true)
        protected String remarques;
        @XmlElement(required = true)
        protected Balises.Balise.Url url;
        @XmlElement(name = "url_histo", required = true)
        protected Balises.Balise.UrlHisto urlHisto;
        protected int active;
        protected int forKyte;

        /**
         * Obtiene el valor de la propiedad idBalise.
         * 
         */
        public int getIdBalise() {
            return idBalise;
        }

        /**
         * Define el valor de la propiedad idBalise.
         * 
         */
        public void setIdBalise(int value) {
            this.idBalise = value;
        }

        /**
         * Obtiene el valor de la propiedad nom.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNom() {
            return nom;
        }

        /**
         * Define el valor de la propiedad nom.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNom(String value) {
            this.nom = value;
        }

        /**
         * Obtiene el valor de la propiedad departement.
         * 
         * @return
         *     possible object is
         *     {@link Balises.Balise.Departement }
         *     
         */
        public Balises.Balise.Departement getDepartement() {
            return departement;
        }

        /**
         * Define el valor de la propiedad departement.
         * 
         * @param value
         *     allowed object is
         *     {@link Balises.Balise.Departement }
         *     
         */
        public void setDepartement(Balises.Balise.Departement value) {
            this.departement = value;
        }

        /**
         * Obtiene el valor de la propiedad coord.
         * 
         * @return
         *     possible object is
         *     {@link Balises.Balise.Coord }
         *     
         */
        public Balises.Balise.Coord getCoord() {
            return coord;
        }

        /**
         * Define el valor de la propiedad coord.
         * 
         * @param value
         *     allowed object is
         *     {@link Balises.Balise.Coord }
         *     
         */
        public void setCoord(Balises.Balise.Coord value) {
            this.coord = value;
        }

        /**
         * Obtiene el valor de la propiedad altitude.
         * 
         * @return
         *     possible object is
         *     {@link Balises.Balise.Altitude }
         *     
         */
        public Balises.Balise.Altitude getAltitude() {
            return altitude;
        }

        /**
         * Define el valor de la propiedad altitude.
         * 
         * @param value
         *     allowed object is
         *     {@link Balises.Balise.Altitude }
         *     
         */
        public void setAltitude(Balises.Balise.Altitude value) {
            this.altitude = value;
        }

        /**
         * Obtiene el valor de la propiedad description.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDescription() {
            return description;
        }

        /**
         * Define el valor de la propiedad description.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDescription(String value) {
            this.description = value;
        }

        /**
         * Obtiene el valor de la propiedad remarques.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRemarques() {
            return remarques;
        }

        /**
         * Define el valor de la propiedad remarques.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRemarques(String value) {
            this.remarques = value;
        }

        /**
         * Obtiene el valor de la propiedad url.
         * 
         * @return
         *     possible object is
         *     {@link Balises.Balise.Url }
         *     
         */
        public Balises.Balise.Url getUrl() {
            return url;
        }

        /**
         * Define el valor de la propiedad url.
         * 
         * @param value
         *     allowed object is
         *     {@link Balises.Balise.Url }
         *     
         */
        public void setUrl(Balises.Balise.Url value) {
            this.url = value;
        }

        /**
         * Obtiene el valor de la propiedad urlHisto.
         * 
         * @return
         *     possible object is
         *     {@link Balises.Balise.UrlHisto }
         *     
         */
        public Balises.Balise.UrlHisto getUrlHisto() {
            return urlHisto;
        }

        /**
         * Define el valor de la propiedad urlHisto.
         * 
         * @param value
         *     allowed object is
         *     {@link Balises.Balise.UrlHisto }
         *     
         */
        public void setUrlHisto(Balises.Balise.UrlHisto value) {
            this.urlHisto = value;
        }

        /**
         * Obtiene el valor de la propiedad active.
         * 
         */
        public int getActive() {
            return active;
        }

        /**
         * Define el valor de la propiedad active.
         * 
         */
        public void setActive(int value) {
            this.active = value;
        }

        /**
         * Obtiene el valor de la propiedad forKyte.
         * 
         */
        public int getForKyte() {
            return forKyte;
        }

        /**
         * Define el valor de la propiedad forKyte.
         * 
         */
        public void setForKyte(int value) {
            this.forKyte = value;
        }


        /**
         * <p>Clase Java para anonymous complex type.
         * 
         * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}int" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Altitude {

            @XmlAttribute(name = "value")
            protected Integer value;

            /**
             * Obtiene el valor de la propiedad value.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getValue() {
                return value;
            }

            /**
             * Define el valor de la propiedad value.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setValue(Integer value) {
                this.value = value;
            }

        }


        /**
         * <p>Clase Java para anonymous complex type.
         * 
         * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;attribute name="lat" type="{http://www.w3.org/2001/XMLSchema}double" />
         *       &lt;attribute name="lon" type="{http://www.w3.org/2001/XMLSchema}double" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Coord {

            @XmlAttribute(name = "lat")
            protected Double lat;
            @XmlAttribute(name = "lon")
            protected Double lon;

            /**
             * Obtiene el valor de la propiedad lat.
             * 
             * @return
             *     possible object is
             *     {@link Double }
             *     
             */
            public Double getLat() {
                return lat;
            }

            /**
             * Define el valor de la propiedad lat.
             * 
             * @param value
             *     allowed object is
             *     {@link Double }
             *     
             */
            public void setLat(Double value) {
                this.lat = value;
            }

            /**
             * Obtiene el valor de la propiedad lon.
             * 
             * @return
             *     possible object is
             *     {@link Double }
             *     
             */
            public Double getLon() {
                return lon;
            }

            /**
             * Define el valor de la propiedad lon.
             * 
             * @param value
             *     allowed object is
             *     {@link Double }
             *     
             */
            public void setLon(Double value) {
                this.lon = value;
            }

        }


        /**
         * <p>Clase Java para anonymous complex type.
         * 
         * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}int" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Departement {

            @XmlAttribute(name = "value")
            protected Integer value;

            /**
             * Obtiene el valor de la propiedad value.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getValue() {
                return value;
            }

            /**
             * Define el valor de la propiedad value.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setValue(Integer value) {
                this.value = value;
            }

        }


        /**
         * <p>Clase Java para anonymous complex type.
         * 
         * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Url {

            @XmlAttribute(name = "value")
            protected String value;

            /**
             * Obtiene el valor de la propiedad value.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getValue() {
                return value;
            }

            /**
             * Define el valor de la propiedad value.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setValue(String value) {
                this.value = value;
            }

        }


        /**
         * <p>Clase Java para anonymous complex type.
         * 
         * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class UrlHisto {

            @XmlAttribute(name = "value")
            protected String value;

            /**
             * Obtiene el valor de la propiedad value.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getValue() {
                return value;
            }

            /**
             * Define el valor de la propiedad value.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setValue(String value) {
                this.value = value;
            }

        }

    }

}
