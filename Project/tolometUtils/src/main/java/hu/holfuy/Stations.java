//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2016.04.07 a las 05:53:42 PM CEST 
//


package hu.holfuy;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType
@XmlRootElement(name = "STATIONS")
public class Stations {

    @XmlElement(name = "STATION", required = true)
    protected List<Station> station;

    /**
     * Gets the value of the station property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the station property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSTATION().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Station }
     * 
     * 
     */
    public List<Station> getSTATION() {
        if (station == null) {
            station = new ArrayList<Station>();
        }
        return this.station;
    }

    @XmlAccessorType(XmlAccessType.PROPERTY)
    @XmlType
    public static class Station {

        @XmlElement(name = "ID", required = true)
        protected String id;
        @XmlElement(name = "NAME", required = true)
        protected String name;
        @XmlElement(name = "LOCATION", required = true)
        protected Station.LOCATION location;
        @XmlElement(name = "DIRECTIONS", required = true)
        protected Station.DIRECTIONS directions;
        @XmlElement(name = "INFO", required = true)
        protected Object info;

        /**
         * Obtiene el valor de la propiedad id.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getID() {
            return id;
        }

        /**
         * Define el valor de la propiedad id.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setID(String value) {
            this.id = value;
        }

        /**
         * Obtiene el valor de la propiedad name.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNAME() {
            return name;
        }

        /**
         * Define el valor de la propiedad name.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNAME(String value) {
            this.name = value;
        }

        /**
         * Obtiene el valor de la propiedad location.
         * 
         * @return
         *     possible object is
         *     {@link Station.LOCATION }
         *     
         */
        public Station.LOCATION getLOCATION() {
            return location;
        }

        /**
         * Define el valor de la propiedad location.
         * 
         * @param value
         *     allowed object is
         *     {@link Station.LOCATION }
         *     
         */
        public void setLOCATION(Station.LOCATION value) {
            this.location = value;
        }

        /**
         * Obtiene el valor de la propiedad directions.
         * 
         * @return
         *     possible object is
         *     {@link Station.DIRECTIONS }
         *     
         */
        public Station.DIRECTIONS getDIRECTIONS() {
            return directions;
        }

        /**
         * Define el valor de la propiedad directions.
         * 
         * @param value
         *     allowed object is
         *     {@link Station.DIRECTIONS }
         *     
         */
        public void setDIRECTIONS(Station.DIRECTIONS value) {
            this.directions = value;
        }

        /**
         * Obtiene el valor de la propiedad info.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getINFO() {
            return info;
        }

        /**
         * Define el valor de la propiedad info.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setINFO(Object value) {
            this.info = value;
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
         *         &lt;element name="OK">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="START" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *                   &lt;element name="STOP" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="GOOD">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="STARTG" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *                   &lt;element name="STOPG" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
        @XmlAccessorType(XmlAccessType.PROPERTY)
        @XmlType
        public static class DIRECTIONS {

            @XmlElement(name = "OK", required = true)
            protected Station.DIRECTIONS.OK ok;
            @XmlElement(name = "GOOD", required = true)
            protected Station.DIRECTIONS.GOOD good;

            /**
             * Obtiene el valor de la propiedad ok.
             * 
             * @return
             *     possible object is
             *     {@link Station.DIRECTIONS.OK }
             *     
             */
            public Station.DIRECTIONS.OK getOK() {
                return ok;
            }

            /**
             * Define el valor de la propiedad ok.
             * 
             * @param value
             *     allowed object is
             *     {@link Station.DIRECTIONS.OK }
             *     
             */
            public void setOK(Station.DIRECTIONS.OK value) {
                this.ok = value;
            }

            /**
             * Obtiene el valor de la propiedad good.
             * 
             * @return
             *     possible object is
             *     {@link Station.DIRECTIONS.GOOD }
             *     
             */
            public Station.DIRECTIONS.GOOD getGOOD() {
                return good;
            }

            /**
             * Define el valor de la propiedad good.
             * 
             * @param value
             *     allowed object is
             *     {@link Station.DIRECTIONS.GOOD }
             *     
             */
            public void setGOOD(Station.DIRECTIONS.GOOD value) {
                this.good = value;
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
             *         &lt;element name="STARTG" type="{http://www.w3.org/2001/XMLSchema}int"/>
             *         &lt;element name="STOPG" type="{http://www.w3.org/2001/XMLSchema}int"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.PROPERTY)
            @XmlType
            public static class GOOD {

                @XmlElement(name = "STARTG")
                protected int startg;
                @XmlElement(name = "STOPG")
                protected int stopg;

                /**
                 * Obtiene el valor de la propiedad startg.
                 * 
                 */
                public int getSTARTG() {
                    return startg;
                }

                /**
                 * Define el valor de la propiedad startg.
                 * 
                 */
                public void setSTARTG(int value) {
                    this.startg = value;
                }

                /**
                 * Obtiene el valor de la propiedad stopg.
                 * 
                 */
                public int getSTOPG() {
                    return stopg;
                }

                /**
                 * Define el valor de la propiedad stopg.
                 * 
                 */
                public void setSTOPG(int value) {
                    this.stopg = value;
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
             *       &lt;sequence>
             *         &lt;element name="START" type="{http://www.w3.org/2001/XMLSchema}int"/>
             *         &lt;element name="STOP" type="{http://www.w3.org/2001/XMLSchema}int"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.PROPERTY)
            @XmlType
            public static class OK {

                @XmlElement(name = "START")
                protected int start;
                @XmlElement(name = "STOP")
                protected int stop;

                /**
                 * Obtiene el valor de la propiedad start.
                 * 
                 */
                public int getSTART() {
                    return start;
                }

                /**
                 * Define el valor de la propiedad start.
                 * 
                 */
                public void setSTART(int value) {
                    this.start = value;
                }

                /**
                 * Obtiene el valor de la propiedad stop.
                 * 
                 */
                public int getSTOP() {
                    return stop;
                }

                /**
                 * Define el valor de la propiedad stop.
                 * 
                 */
                public void setSTOP(int value) {
                    this.stop = value;
                }

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
         *       &lt;sequence>
         *         &lt;element name="LAT" type="{http://www.w3.org/2001/XMLSchema}double"/>
         *         &lt;element name="LONG" type="{http://www.w3.org/2001/XMLSchema}double"/>
         *         &lt;element name="ALT" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *         &lt;element name="COUNTRY" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="COUNTRY_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.PROPERTY)
        @XmlType
        public static class LOCATION {

            @XmlElement(name = "LAT")
            protected double lat;
            @XmlElement(name = "LONG")
            protected double _long;
            @XmlElement(name = "ALT")
            protected int alt;
            @XmlElement(name = "COUNTRY", required = true)
            protected String country;
            @XmlElement(name = "COUNTRY_NAME", required = true)
            protected String countryname;

            /**
             * Obtiene el valor de la propiedad lat.
             * 
             */
            public double getLAT() {
                return lat;
            }

            /**
             * Define el valor de la propiedad lat.
             * 
             */
            public void setLAT(double value) {
                this.lat = value;
            }

            /**
             * Obtiene el valor de la propiedad long.
             * 
             */
            public double getLONG() {
                return _long;
            }

            /**
             * Define el valor de la propiedad long.
             * 
             */
            public void setLONG(double value) {
                this._long = value;
            }

            /**
             * Obtiene el valor de la propiedad alt.
             * 
             */
            public int getALT() {
                return alt;
            }

            /**
             * Define el valor de la propiedad alt.
             * 
             */
            public void setALT(int value) {
                this.alt = value;
            }

            /**
             * Obtiene el valor de la propiedad country.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCOUNTRY() {
                return country;
            }

            /**
             * Define el valor de la propiedad country.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCOUNTRY(String value) {
                this.country = value;
            }

            /**
             * Obtiene el valor de la propiedad countryname.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCOUNTRYNAME() {
                return countryname;
            }

            /**
             * Define el valor de la propiedad countryname.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCOUNTRYNAME(String value) {
                this.countryname = value;
            }

        }

    }

}
