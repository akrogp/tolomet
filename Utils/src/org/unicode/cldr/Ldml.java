//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2015.04.20 a las 08:51:05 PM CEST 
//


package org.unicode.cldr;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


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
 *         &lt;element name="identity">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="version">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                           &lt;attribute name="number" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="generation">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                           &lt;attribute name="date" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="language">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="localeDisplayNames">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="localeDisplayPattern">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="localePattern" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="localeSeparator" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="localeKeyTypePattern" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="languages">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="language" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;simpleContent>
 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                     &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="alt" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/extension>
 *                                 &lt;/simpleContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="scripts">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="script" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;simpleContent>
 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                     &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="alt" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/extension>
 *                                 &lt;/simpleContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="territories">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="territory" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;simpleContent>
 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                     &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="alt" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/extension>
 *                                 &lt;/simpleContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="variants">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="variant" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;simpleContent>
 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                     &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/extension>
 *                                 &lt;/simpleContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="keys">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="key" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;simpleContent>
 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                     &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/extension>
 *                                 &lt;/simpleContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="types">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="type" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;simpleContent>
 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                     &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="key" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/extension>
 *                                 &lt;/simpleContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="transformNames">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="transformName" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;simpleContent>
 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                     &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/extension>
 *                                 &lt;/simpleContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="measurementSystemNames">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="measurementSystemName" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;simpleContent>
 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                     &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/extension>
 *                                 &lt;/simpleContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="codePatterns">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="codePattern" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;simpleContent>
 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                     &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/extension>
 *                                 &lt;/simpleContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="contextTransforms">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="contextTransformUsage" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="contextTransform" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;simpleContent>
 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                     &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/extension>
 *                                 &lt;/simpleContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="characters">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="exemplarCharacters" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="ellipsis" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="moreInformation" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="delimiters">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="quotationStart" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="quotationEnd" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="alternateQuotationStart" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="alternateQuotationEnd" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="dates">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="calendars">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="calendar" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="months" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="monthContext" maxOccurs="unbounded" minOccurs="0">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="monthWidth" maxOccurs="unbounded" minOccurs="0">
 *                                                             &lt;complexType>
 *                                                               &lt;complexContent>
 *                                                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                                   &lt;sequence>
 *                                                                     &lt;element name="month" maxOccurs="unbounded" minOccurs="0">
 *                                                                       &lt;complexType>
 *                                                                         &lt;simpleContent>
 *                                                                           &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                                                             &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *                                                                           &lt;/extension>
 *                                                                         &lt;/simpleContent>
 *                                                                       &lt;/complexType>
 *                                                                     &lt;/element>
 *                                                                   &lt;/sequence>
 *                                                                   &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                                 &lt;/restriction>
 *                                                               &lt;/complexContent>
 *                                                             &lt;/complexType>
 *                                                           &lt;/element>
 *                                                         &lt;/sequence>
 *                                                         &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="days" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="dayContext" maxOccurs="unbounded" minOccurs="0">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="dayWidth" maxOccurs="unbounded" minOccurs="0">
 *                                                             &lt;complexType>
 *                                                               &lt;complexContent>
 *                                                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                                   &lt;sequence>
 *                                                                     &lt;element name="day" maxOccurs="unbounded" minOccurs="0">
 *                                                                       &lt;complexType>
 *                                                                         &lt;simpleContent>
 *                                                                           &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                                                             &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                                           &lt;/extension>
 *                                                                         &lt;/simpleContent>
 *                                                                       &lt;/complexType>
 *                                                                     &lt;/element>
 *                                                                   &lt;/sequence>
 *                                                                   &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                                 &lt;/restriction>
 *                                                               &lt;/complexContent>
 *                                                             &lt;/complexType>
 *                                                           &lt;/element>
 *                                                         &lt;/sequence>
 *                                                         &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="quarters" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="quarterContext" maxOccurs="unbounded" minOccurs="0">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="quarterWidth" maxOccurs="unbounded" minOccurs="0">
 *                                                             &lt;complexType>
 *                                                               &lt;complexContent>
 *                                                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                                   &lt;sequence>
 *                                                                     &lt;element name="quarter" maxOccurs="unbounded" minOccurs="0">
 *                                                                       &lt;complexType>
 *                                                                         &lt;simpleContent>
 *                                                                           &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                                                             &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *                                                                           &lt;/extension>
 *                                                                         &lt;/simpleContent>
 *                                                                       &lt;/complexType>
 *                                                                     &lt;/element>
 *                                                                   &lt;/sequence>
 *                                                                   &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                                 &lt;/restriction>
 *                                                               &lt;/complexContent>
 *                                                             &lt;/complexType>
 *                                                           &lt;/element>
 *                                                         &lt;/sequence>
 *                                                         &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="dayPeriods" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="dayPeriodContext">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="dayPeriodWidth" maxOccurs="unbounded" minOccurs="0">
 *                                                             &lt;complexType>
 *                                                               &lt;complexContent>
 *                                                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                                   &lt;sequence>
 *                                                                     &lt;element name="dayPeriod" maxOccurs="unbounded" minOccurs="0">
 *                                                                       &lt;complexType>
 *                                                                         &lt;simpleContent>
 *                                                                           &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                                                             &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                                           &lt;/extension>
 *                                                                         &lt;/simpleContent>
 *                                                                       &lt;/complexType>
 *                                                                     &lt;/element>
 *                                                                   &lt;/sequence>
 *                                                                   &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                                 &lt;/restriction>
 *                                                               &lt;/complexContent>
 *                                                             &lt;/complexType>
 *                                                           &lt;/element>
 *                                                         &lt;/sequence>
 *                                                         &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="eras" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="eraNames" minOccurs="0">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="era" maxOccurs="unbounded" minOccurs="0">
 *                                                             &lt;complexType>
 *                                                               &lt;simpleContent>
 *                                                                 &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                                                   &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *                                                                   &lt;attribute name="alt" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                                 &lt;/extension>
 *                                                               &lt;/simpleContent>
 *                                                             &lt;/complexType>
 *                                                           &lt;/element>
 *                                                         &lt;/sequence>
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                                 &lt;element name="eraAbbr">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="era" maxOccurs="unbounded" minOccurs="0">
 *                                                             &lt;complexType>
 *                                                               &lt;simpleContent>
 *                                                                 &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                                                   &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *                                                                   &lt;attribute name="alt" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                                   &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                                 &lt;/extension>
 *                                                               &lt;/simpleContent>
 *                                                             &lt;/complexType>
 *                                                           &lt;/element>
 *                                                         &lt;/sequence>
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="dateFormats" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="dateFormatLength" maxOccurs="unbounded" minOccurs="0">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="dateFormat">
 *                                                             &lt;complexType>
 *                                                               &lt;complexContent>
 *                                                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                                   &lt;sequence>
 *                                                                     &lt;element name="pattern">
 *                                                                       &lt;complexType>
 *                                                                         &lt;simpleContent>
 *                                                                           &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                                                             &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                                           &lt;/extension>
 *                                                                         &lt;/simpleContent>
 *                                                                       &lt;/complexType>
 *                                                                     &lt;/element>
 *                                                                   &lt;/sequence>
 *                                                                 &lt;/restriction>
 *                                                               &lt;/complexContent>
 *                                                             &lt;/complexType>
 *                                                           &lt;/element>
 *                                                         &lt;/sequence>
 *                                                         &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="timeFormats" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="timeFormatLength" maxOccurs="unbounded" minOccurs="0">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="timeFormat">
 *                                                             &lt;complexType>
 *                                                               &lt;complexContent>
 *                                                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                                   &lt;sequence>
 *                                                                     &lt;element name="pattern" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                                   &lt;/sequence>
 *                                                                 &lt;/restriction>
 *                                                               &lt;/complexContent>
 *                                                             &lt;/complexType>
 *                                                           &lt;/element>
 *                                                         &lt;/sequence>
 *                                                         &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="dateTimeFormats" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="dateTimeFormatLength" maxOccurs="unbounded" minOccurs="0">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="dateTimeFormat">
 *                                                             &lt;complexType>
 *                                                               &lt;complexContent>
 *                                                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                                   &lt;sequence>
 *                                                                     &lt;element name="pattern" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                                   &lt;/sequence>
 *                                                                 &lt;/restriction>
 *                                                               &lt;/complexContent>
 *                                                             &lt;/complexType>
 *                                                           &lt;/element>
 *                                                         &lt;/sequence>
 *                                                         &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                                 &lt;element name="availableFormats">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="dateFormatItem" maxOccurs="unbounded" minOccurs="0">
 *                                                             &lt;complexType>
 *                                                               &lt;simpleContent>
 *                                                                 &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                                                   &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                                   &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                                 &lt;/extension>
 *                                                               &lt;/simpleContent>
 *                                                             &lt;/complexType>
 *                                                           &lt;/element>
 *                                                         &lt;/sequence>
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                                 &lt;element name="appendItems" minOccurs="0">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="appendItem">
 *                                                             &lt;complexType>
 *                                                               &lt;simpleContent>
 *                                                                 &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                                                   &lt;attribute name="request" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                                 &lt;/extension>
 *                                                               &lt;/simpleContent>
 *                                                             &lt;/complexType>
 *                                                           &lt;/element>
 *                                                         &lt;/sequence>
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                                 &lt;element name="intervalFormats" minOccurs="0">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="intervalFormatFallback" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                           &lt;element name="intervalFormatItem" maxOccurs="unbounded" minOccurs="0">
 *                                                             &lt;complexType>
 *                                                               &lt;complexContent>
 *                                                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                                   &lt;sequence>
 *                                                                     &lt;element name="greatestDifference" maxOccurs="unbounded" minOccurs="0">
 *                                                                       &lt;complexType>
 *                                                                         &lt;simpleContent>
 *                                                                           &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                                                             &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                                             &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                                           &lt;/extension>
 *                                                                         &lt;/simpleContent>
 *                                                                       &lt;/complexType>
 *                                                                     &lt;/element>
 *                                                                   &lt;/sequence>
 *                                                                   &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                                 &lt;/restriction>
 *                                                               &lt;/complexContent>
 *                                                             &lt;/complexType>
 *                                                           &lt;/element>
 *                                                         &lt;/sequence>
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                     &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="fields">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="field" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="displayName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="relative" maxOccurs="unbounded" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;simpleContent>
 *                                             &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                               &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *                                             &lt;/extension>
 *                                           &lt;/simpleContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="relativeTime" maxOccurs="unbounded" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="relativeTimePattern" maxOccurs="unbounded" minOccurs="0">
 *                                                   &lt;complexType>
 *                                                     &lt;simpleContent>
 *                                                       &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                                         &lt;attribute name="count" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                       &lt;/extension>
 *                                                     &lt;/simpleContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                               &lt;/sequence>
 *                                               &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                     &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="timeZoneNames">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="hourFormat" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="gmtFormat" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="gmtZeroFormat" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="regionFormat" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;simpleContent>
 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                     &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/extension>
 *                                 &lt;/simpleContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="fallbackFormat" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="zone" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="long" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="daylight" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="exemplarCity" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                     &lt;/sequence>
 *                                     &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="metazone" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="long">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="generic" minOccurs="0">
 *                                                   &lt;complexType>
 *                                                     &lt;simpleContent>
 *                                                       &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                                         &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                       &lt;/extension>
 *                                                     &lt;/simpleContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                                 &lt;element name="standard">
 *                                                   &lt;complexType>
 *                                                     &lt;simpleContent>
 *                                                       &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                                         &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                       &lt;/extension>
 *                                                     &lt;/simpleContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                                 &lt;element name="daylight" minOccurs="0">
 *                                                   &lt;complexType>
 *                                                     &lt;simpleContent>
 *                                                       &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                                         &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                       &lt;/extension>
 *                                                     &lt;/simpleContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="short" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="generic" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                                 &lt;element name="standard" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                 &lt;element name="daylight" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                     &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="numbers">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="defaultNumberingSystem">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                           &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="otherNumberingSystems">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="native">
 *                               &lt;complexType>
 *                                 &lt;simpleContent>
 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                     &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/extension>
 *                                 &lt;/simpleContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="minimumGroupingDigits">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>byte">
 *                           &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="symbols">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="decimal" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="group" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="list" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="percentSign" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="plusSign" type="{http://www.w3.org/2001/XMLSchema}byte"/>
 *                             &lt;element name="minusSign" type="{http://www.w3.org/2001/XMLSchema}byte"/>
 *                             &lt;element name="exponential" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="superscriptingExponent" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="perMille" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="infinity" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="nan" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *                             &lt;element name="timeSeparator">
 *                               &lt;complexType>
 *                                 &lt;simpleContent>
 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                     &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/extension>
 *                                 &lt;/simpleContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                           &lt;attribute name="numberSystem" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="decimalFormats">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="decimalFormatLength" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="decimalFormat">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="pattern" maxOccurs="unbounded" minOccurs="0">
 *                                                   &lt;complexType>
 *                                                     &lt;simpleContent>
 *                                                       &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                                         &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}long" />
 *                                                         &lt;attribute name="count" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                       &lt;/extension>
 *                                                     &lt;/simpleContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                     &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                           &lt;attribute name="numberSystem" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="scientificFormats">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="scientificFormatLength">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="scientificFormat">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="pattern" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                           &lt;attribute name="numberSystem" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="percentFormats">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="percentFormatLength">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="percentFormat">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="pattern" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                           &lt;attribute name="numberSystem" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="currencyFormats">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="currencyFormatLength">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="currencyFormat" maxOccurs="unbounded" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="pattern" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                               &lt;/sequence>
 *                                               &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="unitPattern" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;simpleContent>
 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                     &lt;attribute name="count" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/extension>
 *                                 &lt;/simpleContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                           &lt;attribute name="numberSystem" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="currencies">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="currency" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="displayName" maxOccurs="unbounded" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;simpleContent>
 *                                             &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                               &lt;attribute name="count" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                               &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                             &lt;/extension>
 *                                           &lt;/simpleContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="symbol" maxOccurs="unbounded" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;simpleContent>
 *                                             &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                               &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                               &lt;attribute name="alt" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                             &lt;/extension>
 *                                           &lt;/simpleContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                     &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="miscPatterns">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="pattern" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;simpleContent>
 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                     &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/extension>
 *                                 &lt;/simpleContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                           &lt;attribute name="numberSystem" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="units">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="unitLength" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="compoundUnit">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="compoundUnitPattern">
 *                                         &lt;complexType>
 *                                           &lt;simpleContent>
 *                                             &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                               &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                             &lt;/extension>
 *                                           &lt;/simpleContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                     &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="unit" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="displayName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                       &lt;element name="unitPattern" maxOccurs="unbounded" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;simpleContent>
 *                                             &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                               &lt;attribute name="count" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                               &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                             &lt;/extension>
 *                                           &lt;/simpleContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="perUnitPattern" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                     &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="durationUnit" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="durationUnitPattern" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="listPatterns">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="listPattern" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="listPatternPart" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;simpleContent>
 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                     &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/extension>
 *                                 &lt;/simpleContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="posix">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="messages">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="yesstr" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="nostr" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
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
    "identity",
    "localeDisplayNames",
    "contextTransforms",
    "characters",
    "delimiters",
    "dates",
    "numbers",
    "units",
    "listPatterns",
    "posix"
})
@XmlRootElement(name = "ldml")
public class Ldml {

    @XmlElement(required = true)
    protected Ldml.Identity identity;
    @XmlElement(required = true)
    protected Ldml.LocaleDisplayNames localeDisplayNames;
    @XmlElement(required = true)
    protected Ldml.ContextTransforms contextTransforms;
    @XmlElement(required = true)
    protected Ldml.Characters characters;
    @XmlElement(required = true)
    protected Ldml.Delimiters delimiters;
    @XmlElement(required = true)
    protected Ldml.Dates dates;
    @XmlElement(required = true)
    protected Ldml.Numbers numbers;
    @XmlElement(required = true)
    protected Ldml.Units units;
    @XmlElement(required = true)
    protected Ldml.ListPatterns listPatterns;
    @XmlElement(required = true)
    protected Ldml.Posix posix;

    /**
     * Obtiene el valor de la propiedad identity.
     * 
     * @return
     *     possible object is
     *     {@link Ldml.Identity }
     *     
     */
    public Ldml.Identity getIdentity() {
        return identity;
    }

    /**
     * Define el valor de la propiedad identity.
     * 
     * @param value
     *     allowed object is
     *     {@link Ldml.Identity }
     *     
     */
    public void setIdentity(Ldml.Identity value) {
        this.identity = value;
    }

    /**
     * Obtiene el valor de la propiedad localeDisplayNames.
     * 
     * @return
     *     possible object is
     *     {@link Ldml.LocaleDisplayNames }
     *     
     */
    public Ldml.LocaleDisplayNames getLocaleDisplayNames() {
        return localeDisplayNames;
    }

    /**
     * Define el valor de la propiedad localeDisplayNames.
     * 
     * @param value
     *     allowed object is
     *     {@link Ldml.LocaleDisplayNames }
     *     
     */
    public void setLocaleDisplayNames(Ldml.LocaleDisplayNames value) {
        this.localeDisplayNames = value;
    }

    /**
     * Obtiene el valor de la propiedad contextTransforms.
     * 
     * @return
     *     possible object is
     *     {@link Ldml.ContextTransforms }
     *     
     */
    public Ldml.ContextTransforms getContextTransforms() {
        return contextTransforms;
    }

    /**
     * Define el valor de la propiedad contextTransforms.
     * 
     * @param value
     *     allowed object is
     *     {@link Ldml.ContextTransforms }
     *     
     */
    public void setContextTransforms(Ldml.ContextTransforms value) {
        this.contextTransforms = value;
    }

    /**
     * Obtiene el valor de la propiedad characters.
     * 
     * @return
     *     possible object is
     *     {@link Ldml.Characters }
     *     
     */
    public Ldml.Characters getCharacters() {
        return characters;
    }

    /**
     * Define el valor de la propiedad characters.
     * 
     * @param value
     *     allowed object is
     *     {@link Ldml.Characters }
     *     
     */
    public void setCharacters(Ldml.Characters value) {
        this.characters = value;
    }

    /**
     * Obtiene el valor de la propiedad delimiters.
     * 
     * @return
     *     possible object is
     *     {@link Ldml.Delimiters }
     *     
     */
    public Ldml.Delimiters getDelimiters() {
        return delimiters;
    }

    /**
     * Define el valor de la propiedad delimiters.
     * 
     * @param value
     *     allowed object is
     *     {@link Ldml.Delimiters }
     *     
     */
    public void setDelimiters(Ldml.Delimiters value) {
        this.delimiters = value;
    }

    /**
     * Obtiene el valor de la propiedad dates.
     * 
     * @return
     *     possible object is
     *     {@link Ldml.Dates }
     *     
     */
    public Ldml.Dates getDates() {
        return dates;
    }

    /**
     * Define el valor de la propiedad dates.
     * 
     * @param value
     *     allowed object is
     *     {@link Ldml.Dates }
     *     
     */
    public void setDates(Ldml.Dates value) {
        this.dates = value;
    }

    /**
     * Obtiene el valor de la propiedad numbers.
     * 
     * @return
     *     possible object is
     *     {@link Ldml.Numbers }
     *     
     */
    public Ldml.Numbers getNumbers() {
        return numbers;
    }

    /**
     * Define el valor de la propiedad numbers.
     * 
     * @param value
     *     allowed object is
     *     {@link Ldml.Numbers }
     *     
     */
    public void setNumbers(Ldml.Numbers value) {
        this.numbers = value;
    }

    /**
     * Obtiene el valor de la propiedad units.
     * 
     * @return
     *     possible object is
     *     {@link Ldml.Units }
     *     
     */
    public Ldml.Units getUnits() {
        return units;
    }

    /**
     * Define el valor de la propiedad units.
     * 
     * @param value
     *     allowed object is
     *     {@link Ldml.Units }
     *     
     */
    public void setUnits(Ldml.Units value) {
        this.units = value;
    }

    /**
     * Obtiene el valor de la propiedad listPatterns.
     * 
     * @return
     *     possible object is
     *     {@link Ldml.ListPatterns }
     *     
     */
    public Ldml.ListPatterns getListPatterns() {
        return listPatterns;
    }

    /**
     * Define el valor de la propiedad listPatterns.
     * 
     * @param value
     *     allowed object is
     *     {@link Ldml.ListPatterns }
     *     
     */
    public void setListPatterns(Ldml.ListPatterns value) {
        this.listPatterns = value;
    }

    /**
     * Obtiene el valor de la propiedad posix.
     * 
     * @return
     *     possible object is
     *     {@link Ldml.Posix }
     *     
     */
    public Ldml.Posix getPosix() {
        return posix;
    }

    /**
     * Define el valor de la propiedad posix.
     * 
     * @param value
     *     allowed object is
     *     {@link Ldml.Posix }
     *     
     */
    public void setPosix(Ldml.Posix value) {
        this.posix = value;
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
     *         &lt;element name="exemplarCharacters" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/extension>
     *             &lt;/simpleContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="ellipsis" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/extension>
     *             &lt;/simpleContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="moreInformation" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "exemplarCharacters",
        "ellipsis",
        "moreInformation"
    })
    public static class Characters {

        protected List<Ldml.Characters.ExemplarCharacters> exemplarCharacters;
        protected List<Ldml.Characters.Ellipsis> ellipsis;
        @XmlElement(required = true)
        protected String moreInformation;

        /**
         * Gets the value of the exemplarCharacters property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the exemplarCharacters property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getExemplarCharacters().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Ldml.Characters.ExemplarCharacters }
         * 
         * 
         */
        public List<Ldml.Characters.ExemplarCharacters> getExemplarCharacters() {
            if (exemplarCharacters == null) {
                exemplarCharacters = new ArrayList<Ldml.Characters.ExemplarCharacters>();
            }
            return this.exemplarCharacters;
        }

        /**
         * Gets the value of the ellipsis property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the ellipsis property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEllipsis().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Ldml.Characters.Ellipsis }
         * 
         * 
         */
        public List<Ldml.Characters.Ellipsis> getEllipsis() {
            if (ellipsis == null) {
                ellipsis = new ArrayList<Ldml.Characters.Ellipsis>();
            }
            return this.ellipsis;
        }

        /**
         * Obtiene el valor de la propiedad moreInformation.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMoreInformation() {
            return moreInformation;
        }

        /**
         * Define el valor de la propiedad moreInformation.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMoreInformation(String value) {
            this.moreInformation = value;
        }


        /**
         * <p>Clase Java para anonymous complex type.
         * 
         * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class Ellipsis {

            @XmlValue
            protected String value;
            @XmlAttribute(name = "type")
            protected String type;

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

            /**
             * Obtiene el valor de la propiedad type.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getType() {
                return type;
            }

            /**
             * Define el valor de la propiedad type.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setType(String value) {
                this.type = value;
            }

        }


        /**
         * <p>Clase Java para anonymous complex type.
         * 
         * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class ExemplarCharacters {

            @XmlValue
            protected String value;
            @XmlAttribute(name = "type")
            protected String type;

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

            /**
             * Obtiene el valor de la propiedad type.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getType() {
                return type;
            }

            /**
             * Define el valor de la propiedad type.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setType(String value) {
                this.type = value;
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
     *         &lt;element name="contextTransformUsage" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="contextTransform" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;simpleContent>
     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/extension>
     *                       &lt;/simpleContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
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
        "contextTransformUsage"
    })
    public static class ContextTransforms {

        protected List<Ldml.ContextTransforms.ContextTransformUsage> contextTransformUsage;

        /**
         * Gets the value of the contextTransformUsage property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the contextTransformUsage property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getContextTransformUsage().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Ldml.ContextTransforms.ContextTransformUsage }
         * 
         * 
         */
        public List<Ldml.ContextTransforms.ContextTransformUsage> getContextTransformUsage() {
            if (contextTransformUsage == null) {
                contextTransformUsage = new ArrayList<Ldml.ContextTransforms.ContextTransformUsage>();
            }
            return this.contextTransformUsage;
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
         *         &lt;element name="contextTransform" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;simpleContent>
         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
         *               &lt;/extension>
         *             &lt;/simpleContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "contextTransform"
        })
        public static class ContextTransformUsage {

            protected List<Ldml.ContextTransforms.ContextTransformUsage.ContextTransform> contextTransform;
            @XmlAttribute(name = "type")
            protected String type;

            /**
             * Gets the value of the contextTransform property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the contextTransform property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getContextTransform().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Ldml.ContextTransforms.ContextTransformUsage.ContextTransform }
             * 
             * 
             */
            public List<Ldml.ContextTransforms.ContextTransformUsage.ContextTransform> getContextTransform() {
                if (contextTransform == null) {
                    contextTransform = new ArrayList<Ldml.ContextTransforms.ContextTransformUsage.ContextTransform>();
                }
                return this.contextTransform;
            }

            /**
             * Obtiene el valor de la propiedad type.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getType() {
                return type;
            }

            /**
             * Define el valor de la propiedad type.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setType(String value) {
                this.type = value;
            }


            /**
             * <p>Clase Java para anonymous complex type.
             * 
             * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;simpleContent>
             *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/extension>
             *   &lt;/simpleContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "value"
            })
            public static class ContextTransform {

                @XmlValue
                protected String value;
                @XmlAttribute(name = "type")
                protected String type;

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

                /**
                 * Obtiene el valor de la propiedad type.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getType() {
                    return type;
                }

                /**
                 * Define el valor de la propiedad type.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setType(String value) {
                    this.type = value;
                }

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
     *         &lt;element name="calendars">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="calendar" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="months" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="monthContext" maxOccurs="unbounded" minOccurs="0">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;sequence>
     *                                                 &lt;element name="monthWidth" maxOccurs="unbounded" minOccurs="0">
     *                                                   &lt;complexType>
     *                                                     &lt;complexContent>
     *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                                         &lt;sequence>
     *                                                           &lt;element name="month" maxOccurs="unbounded" minOccurs="0">
     *                                                             &lt;complexType>
     *                                                               &lt;simpleContent>
     *                                                                 &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                                                                   &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}byte" />
     *                                                                 &lt;/extension>
     *                                                               &lt;/simpleContent>
     *                                                             &lt;/complexType>
     *                                                           &lt;/element>
     *                                                         &lt;/sequence>
     *                                                         &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                                       &lt;/restriction>
     *                                                     &lt;/complexContent>
     *                                                   &lt;/complexType>
     *                                                 &lt;/element>
     *                                               &lt;/sequence>
     *                                               &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                             &lt;/restriction>
     *                                           &lt;/complexContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                             &lt;element name="days" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="dayContext" maxOccurs="unbounded" minOccurs="0">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;sequence>
     *                                                 &lt;element name="dayWidth" maxOccurs="unbounded" minOccurs="0">
     *                                                   &lt;complexType>
     *                                                     &lt;complexContent>
     *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                                         &lt;sequence>
     *                                                           &lt;element name="day" maxOccurs="unbounded" minOccurs="0">
     *                                                             &lt;complexType>
     *                                                               &lt;simpleContent>
     *                                                                 &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                                                                   &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                                                 &lt;/extension>
     *                                                               &lt;/simpleContent>
     *                                                             &lt;/complexType>
     *                                                           &lt;/element>
     *                                                         &lt;/sequence>
     *                                                         &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                                       &lt;/restriction>
     *                                                     &lt;/complexContent>
     *                                                   &lt;/complexType>
     *                                                 &lt;/element>
     *                                               &lt;/sequence>
     *                                               &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                             &lt;/restriction>
     *                                           &lt;/complexContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                             &lt;element name="quarters" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="quarterContext" maxOccurs="unbounded" minOccurs="0">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;sequence>
     *                                                 &lt;element name="quarterWidth" maxOccurs="unbounded" minOccurs="0">
     *                                                   &lt;complexType>
     *                                                     &lt;complexContent>
     *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                                         &lt;sequence>
     *                                                           &lt;element name="quarter" maxOccurs="unbounded" minOccurs="0">
     *                                                             &lt;complexType>
     *                                                               &lt;simpleContent>
     *                                                                 &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                                                                   &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}byte" />
     *                                                                 &lt;/extension>
     *                                                               &lt;/simpleContent>
     *                                                             &lt;/complexType>
     *                                                           &lt;/element>
     *                                                         &lt;/sequence>
     *                                                         &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                                       &lt;/restriction>
     *                                                     &lt;/complexContent>
     *                                                   &lt;/complexType>
     *                                                 &lt;/element>
     *                                               &lt;/sequence>
     *                                               &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                             &lt;/restriction>
     *                                           &lt;/complexContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                             &lt;element name="dayPeriods" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="dayPeriodContext">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;sequence>
     *                                                 &lt;element name="dayPeriodWidth" maxOccurs="unbounded" minOccurs="0">
     *                                                   &lt;complexType>
     *                                                     &lt;complexContent>
     *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                                         &lt;sequence>
     *                                                           &lt;element name="dayPeriod" maxOccurs="unbounded" minOccurs="0">
     *                                                             &lt;complexType>
     *                                                               &lt;simpleContent>
     *                                                                 &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                                                                   &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                                                 &lt;/extension>
     *                                                               &lt;/simpleContent>
     *                                                             &lt;/complexType>
     *                                                           &lt;/element>
     *                                                         &lt;/sequence>
     *                                                         &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                                       &lt;/restriction>
     *                                                     &lt;/complexContent>
     *                                                   &lt;/complexType>
     *                                                 &lt;/element>
     *                                               &lt;/sequence>
     *                                               &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                             &lt;/restriction>
     *                                           &lt;/complexContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                             &lt;element name="eras" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="eraNames" minOccurs="0">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;sequence>
     *                                                 &lt;element name="era" maxOccurs="unbounded" minOccurs="0">
     *                                                   &lt;complexType>
     *                                                     &lt;simpleContent>
     *                                                       &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                                                         &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}byte" />
     *                                                         &lt;attribute name="alt" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                                       &lt;/extension>
     *                                                     &lt;/simpleContent>
     *                                                   &lt;/complexType>
     *                                                 &lt;/element>
     *                                               &lt;/sequence>
     *                                             &lt;/restriction>
     *                                           &lt;/complexContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                       &lt;element name="eraAbbr">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;sequence>
     *                                                 &lt;element name="era" maxOccurs="unbounded" minOccurs="0">
     *                                                   &lt;complexType>
     *                                                     &lt;simpleContent>
     *                                                       &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                                                         &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}byte" />
     *                                                         &lt;attribute name="alt" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                                         &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                                       &lt;/extension>
     *                                                     &lt;/simpleContent>
     *                                                   &lt;/complexType>
     *                                                 &lt;/element>
     *                                               &lt;/sequence>
     *                                             &lt;/restriction>
     *                                           &lt;/complexContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                             &lt;element name="dateFormats" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="dateFormatLength" maxOccurs="unbounded" minOccurs="0">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;sequence>
     *                                                 &lt;element name="dateFormat">
     *                                                   &lt;complexType>
     *                                                     &lt;complexContent>
     *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                                         &lt;sequence>
     *                                                           &lt;element name="pattern">
     *                                                             &lt;complexType>
     *                                                               &lt;simpleContent>
     *                                                                 &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                                                                   &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                                                 &lt;/extension>
     *                                                               &lt;/simpleContent>
     *                                                             &lt;/complexType>
     *                                                           &lt;/element>
     *                                                         &lt;/sequence>
     *                                                       &lt;/restriction>
     *                                                     &lt;/complexContent>
     *                                                   &lt;/complexType>
     *                                                 &lt;/element>
     *                                               &lt;/sequence>
     *                                               &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                             &lt;/restriction>
     *                                           &lt;/complexContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                             &lt;element name="timeFormats" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="timeFormatLength" maxOccurs="unbounded" minOccurs="0">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;sequence>
     *                                                 &lt;element name="timeFormat">
     *                                                   &lt;complexType>
     *                                                     &lt;complexContent>
     *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                                         &lt;sequence>
     *                                                           &lt;element name="pattern" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                                         &lt;/sequence>
     *                                                       &lt;/restriction>
     *                                                     &lt;/complexContent>
     *                                                   &lt;/complexType>
     *                                                 &lt;/element>
     *                                               &lt;/sequence>
     *                                               &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                             &lt;/restriction>
     *                                           &lt;/complexContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                             &lt;element name="dateTimeFormats" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="dateTimeFormatLength" maxOccurs="unbounded" minOccurs="0">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;sequence>
     *                                                 &lt;element name="dateTimeFormat">
     *                                                   &lt;complexType>
     *                                                     &lt;complexContent>
     *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                                         &lt;sequence>
     *                                                           &lt;element name="pattern" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                                         &lt;/sequence>
     *                                                       &lt;/restriction>
     *                                                     &lt;/complexContent>
     *                                                   &lt;/complexType>
     *                                                 &lt;/element>
     *                                               &lt;/sequence>
     *                                               &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                             &lt;/restriction>
     *                                           &lt;/complexContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                       &lt;element name="availableFormats">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;sequence>
     *                                                 &lt;element name="dateFormatItem" maxOccurs="unbounded" minOccurs="0">
     *                                                   &lt;complexType>
     *                                                     &lt;simpleContent>
     *                                                       &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                                                         &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                                         &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                                       &lt;/extension>
     *                                                     &lt;/simpleContent>
     *                                                   &lt;/complexType>
     *                                                 &lt;/element>
     *                                               &lt;/sequence>
     *                                             &lt;/restriction>
     *                                           &lt;/complexContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                       &lt;element name="appendItems" minOccurs="0">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;sequence>
     *                                                 &lt;element name="appendItem">
     *                                                   &lt;complexType>
     *                                                     &lt;simpleContent>
     *                                                       &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                                                         &lt;attribute name="request" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                                       &lt;/extension>
     *                                                     &lt;/simpleContent>
     *                                                   &lt;/complexType>
     *                                                 &lt;/element>
     *                                               &lt;/sequence>
     *                                             &lt;/restriction>
     *                                           &lt;/complexContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                       &lt;element name="intervalFormats" minOccurs="0">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;sequence>
     *                                                 &lt;element name="intervalFormatFallback" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                                 &lt;element name="intervalFormatItem" maxOccurs="unbounded" minOccurs="0">
     *                                                   &lt;complexType>
     *                                                     &lt;complexContent>
     *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                                         &lt;sequence>
     *                                                           &lt;element name="greatestDifference" maxOccurs="unbounded" minOccurs="0">
     *                                                             &lt;complexType>
     *                                                               &lt;simpleContent>
     *                                                                 &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                                                                   &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                                                   &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                                                 &lt;/extension>
     *                                                               &lt;/simpleContent>
     *                                                             &lt;/complexType>
     *                                                           &lt;/element>
     *                                                         &lt;/sequence>
     *                                                         &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                                       &lt;/restriction>
     *                                                     &lt;/complexContent>
     *                                                   &lt;/complexType>
     *                                                 &lt;/element>
     *                                               &lt;/sequence>
     *                                             &lt;/restriction>
     *                                           &lt;/complexContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="fields">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="field" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="displayName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="relative" maxOccurs="unbounded" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;simpleContent>
     *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                                     &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}byte" />
     *                                   &lt;/extension>
     *                                 &lt;/simpleContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                             &lt;element name="relativeTime" maxOccurs="unbounded" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="relativeTimePattern" maxOccurs="unbounded" minOccurs="0">
     *                                         &lt;complexType>
     *                                           &lt;simpleContent>
     *                                             &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                                               &lt;attribute name="count" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                             &lt;/extension>
     *                                           &lt;/simpleContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                     &lt;/sequence>
     *                                     &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="timeZoneNames">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="hourFormat" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="gmtFormat" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="gmtZeroFormat" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="regionFormat" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;simpleContent>
     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/extension>
     *                       &lt;/simpleContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="fallbackFormat" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="zone" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="long" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="daylight" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                             &lt;element name="exemplarCity" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                           &lt;/sequence>
     *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="metazone" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="long">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="generic" minOccurs="0">
     *                                         &lt;complexType>
     *                                           &lt;simpleContent>
     *                                             &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                                               &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                             &lt;/extension>
     *                                           &lt;/simpleContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                       &lt;element name="standard">
     *                                         &lt;complexType>
     *                                           &lt;simpleContent>
     *                                             &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                                               &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                             &lt;/extension>
     *                                           &lt;/simpleContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                       &lt;element name="daylight" minOccurs="0">
     *                                         &lt;complexType>
     *                                           &lt;simpleContent>
     *                                             &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                                               &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                             &lt;/extension>
     *                                           &lt;/simpleContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                             &lt;element name="short" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="generic" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                       &lt;element name="standard" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                       &lt;element name="daylight" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
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
        "calendars",
        "fields",
        "timeZoneNames"
    })
    public static class Dates {

        @XmlElement(required = true)
        protected Ldml.Dates.Calendars calendars;
        @XmlElement(required = true)
        protected Ldml.Dates.Fields fields;
        @XmlElement(required = true)
        protected Ldml.Dates.TimeZoneNames timeZoneNames;

        /**
         * Obtiene el valor de la propiedad calendars.
         * 
         * @return
         *     possible object is
         *     {@link Ldml.Dates.Calendars }
         *     
         */
        public Ldml.Dates.Calendars getCalendars() {
            return calendars;
        }

        /**
         * Define el valor de la propiedad calendars.
         * 
         * @param value
         *     allowed object is
         *     {@link Ldml.Dates.Calendars }
         *     
         */
        public void setCalendars(Ldml.Dates.Calendars value) {
            this.calendars = value;
        }

        /**
         * Obtiene el valor de la propiedad fields.
         * 
         * @return
         *     possible object is
         *     {@link Ldml.Dates.Fields }
         *     
         */
        public Ldml.Dates.Fields getFields() {
            return fields;
        }

        /**
         * Define el valor de la propiedad fields.
         * 
         * @param value
         *     allowed object is
         *     {@link Ldml.Dates.Fields }
         *     
         */
        public void setFields(Ldml.Dates.Fields value) {
            this.fields = value;
        }

        /**
         * Obtiene el valor de la propiedad timeZoneNames.
         * 
         * @return
         *     possible object is
         *     {@link Ldml.Dates.TimeZoneNames }
         *     
         */
        public Ldml.Dates.TimeZoneNames getTimeZoneNames() {
            return timeZoneNames;
        }

        /**
         * Define el valor de la propiedad timeZoneNames.
         * 
         * @param value
         *     allowed object is
         *     {@link Ldml.Dates.TimeZoneNames }
         *     
         */
        public void setTimeZoneNames(Ldml.Dates.TimeZoneNames value) {
            this.timeZoneNames = value;
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
         *         &lt;element name="calendar" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="months" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="monthContext" maxOccurs="unbounded" minOccurs="0">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;sequence>
         *                                       &lt;element name="monthWidth" maxOccurs="unbounded" minOccurs="0">
         *                                         &lt;complexType>
         *                                           &lt;complexContent>
         *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                               &lt;sequence>
         *                                                 &lt;element name="month" maxOccurs="unbounded" minOccurs="0">
         *                                                   &lt;complexType>
         *                                                     &lt;simpleContent>
         *                                                       &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                                                         &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}byte" />
         *                                                       &lt;/extension>
         *                                                     &lt;/simpleContent>
         *                                                   &lt;/complexType>
         *                                                 &lt;/element>
         *                                               &lt;/sequence>
         *                                               &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                                             &lt;/restriction>
         *                                           &lt;/complexContent>
         *                                         &lt;/complexType>
         *                                       &lt;/element>
         *                                     &lt;/sequence>
         *                                     &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                                   &lt;/restriction>
         *                                 &lt;/complexContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                   &lt;element name="days" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="dayContext" maxOccurs="unbounded" minOccurs="0">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;sequence>
         *                                       &lt;element name="dayWidth" maxOccurs="unbounded" minOccurs="0">
         *                                         &lt;complexType>
         *                                           &lt;complexContent>
         *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                               &lt;sequence>
         *                                                 &lt;element name="day" maxOccurs="unbounded" minOccurs="0">
         *                                                   &lt;complexType>
         *                                                     &lt;simpleContent>
         *                                                       &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                                                         &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                                                       &lt;/extension>
         *                                                     &lt;/simpleContent>
         *                                                   &lt;/complexType>
         *                                                 &lt;/element>
         *                                               &lt;/sequence>
         *                                               &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                                             &lt;/restriction>
         *                                           &lt;/complexContent>
         *                                         &lt;/complexType>
         *                                       &lt;/element>
         *                                     &lt;/sequence>
         *                                     &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                                   &lt;/restriction>
         *                                 &lt;/complexContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                   &lt;element name="quarters" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="quarterContext" maxOccurs="unbounded" minOccurs="0">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;sequence>
         *                                       &lt;element name="quarterWidth" maxOccurs="unbounded" minOccurs="0">
         *                                         &lt;complexType>
         *                                           &lt;complexContent>
         *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                               &lt;sequence>
         *                                                 &lt;element name="quarter" maxOccurs="unbounded" minOccurs="0">
         *                                                   &lt;complexType>
         *                                                     &lt;simpleContent>
         *                                                       &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                                                         &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}byte" />
         *                                                       &lt;/extension>
         *                                                     &lt;/simpleContent>
         *                                                   &lt;/complexType>
         *                                                 &lt;/element>
         *                                               &lt;/sequence>
         *                                               &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                                             &lt;/restriction>
         *                                           &lt;/complexContent>
         *                                         &lt;/complexType>
         *                                       &lt;/element>
         *                                     &lt;/sequence>
         *                                     &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                                   &lt;/restriction>
         *                                 &lt;/complexContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                   &lt;element name="dayPeriods" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="dayPeriodContext">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;sequence>
         *                                       &lt;element name="dayPeriodWidth" maxOccurs="unbounded" minOccurs="0">
         *                                         &lt;complexType>
         *                                           &lt;complexContent>
         *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                               &lt;sequence>
         *                                                 &lt;element name="dayPeriod" maxOccurs="unbounded" minOccurs="0">
         *                                                   &lt;complexType>
         *                                                     &lt;simpleContent>
         *                                                       &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                                                         &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                                                       &lt;/extension>
         *                                                     &lt;/simpleContent>
         *                                                   &lt;/complexType>
         *                                                 &lt;/element>
         *                                               &lt;/sequence>
         *                                               &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                                             &lt;/restriction>
         *                                           &lt;/complexContent>
         *                                         &lt;/complexType>
         *                                       &lt;/element>
         *                                     &lt;/sequence>
         *                                     &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                                   &lt;/restriction>
         *                                 &lt;/complexContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                   &lt;element name="eras" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="eraNames" minOccurs="0">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;sequence>
         *                                       &lt;element name="era" maxOccurs="unbounded" minOccurs="0">
         *                                         &lt;complexType>
         *                                           &lt;simpleContent>
         *                                             &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                                               &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}byte" />
         *                                               &lt;attribute name="alt" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                                             &lt;/extension>
         *                                           &lt;/simpleContent>
         *                                         &lt;/complexType>
         *                                       &lt;/element>
         *                                     &lt;/sequence>
         *                                   &lt;/restriction>
         *                                 &lt;/complexContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                             &lt;element name="eraAbbr">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;sequence>
         *                                       &lt;element name="era" maxOccurs="unbounded" minOccurs="0">
         *                                         &lt;complexType>
         *                                           &lt;simpleContent>
         *                                             &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                                               &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}byte" />
         *                                               &lt;attribute name="alt" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                                               &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                                             &lt;/extension>
         *                                           &lt;/simpleContent>
         *                                         &lt;/complexType>
         *                                       &lt;/element>
         *                                     &lt;/sequence>
         *                                   &lt;/restriction>
         *                                 &lt;/complexContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                   &lt;element name="dateFormats" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="dateFormatLength" maxOccurs="unbounded" minOccurs="0">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;sequence>
         *                                       &lt;element name="dateFormat">
         *                                         &lt;complexType>
         *                                           &lt;complexContent>
         *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                               &lt;sequence>
         *                                                 &lt;element name="pattern">
         *                                                   &lt;complexType>
         *                                                     &lt;simpleContent>
         *                                                       &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                                                         &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                                                       &lt;/extension>
         *                                                     &lt;/simpleContent>
         *                                                   &lt;/complexType>
         *                                                 &lt;/element>
         *                                               &lt;/sequence>
         *                                             &lt;/restriction>
         *                                           &lt;/complexContent>
         *                                         &lt;/complexType>
         *                                       &lt;/element>
         *                                     &lt;/sequence>
         *                                     &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                                   &lt;/restriction>
         *                                 &lt;/complexContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                   &lt;element name="timeFormats" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="timeFormatLength" maxOccurs="unbounded" minOccurs="0">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;sequence>
         *                                       &lt;element name="timeFormat">
         *                                         &lt;complexType>
         *                                           &lt;complexContent>
         *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                               &lt;sequence>
         *                                                 &lt;element name="pattern" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                                               &lt;/sequence>
         *                                             &lt;/restriction>
         *                                           &lt;/complexContent>
         *                                         &lt;/complexType>
         *                                       &lt;/element>
         *                                     &lt;/sequence>
         *                                     &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                                   &lt;/restriction>
         *                                 &lt;/complexContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                   &lt;element name="dateTimeFormats" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="dateTimeFormatLength" maxOccurs="unbounded" minOccurs="0">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;sequence>
         *                                       &lt;element name="dateTimeFormat">
         *                                         &lt;complexType>
         *                                           &lt;complexContent>
         *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                               &lt;sequence>
         *                                                 &lt;element name="pattern" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                                               &lt;/sequence>
         *                                             &lt;/restriction>
         *                                           &lt;/complexContent>
         *                                         &lt;/complexType>
         *                                       &lt;/element>
         *                                     &lt;/sequence>
         *                                     &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                                   &lt;/restriction>
         *                                 &lt;/complexContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                             &lt;element name="availableFormats">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;sequence>
         *                                       &lt;element name="dateFormatItem" maxOccurs="unbounded" minOccurs="0">
         *                                         &lt;complexType>
         *                                           &lt;simpleContent>
         *                                             &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                                               &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                                               &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                                             &lt;/extension>
         *                                           &lt;/simpleContent>
         *                                         &lt;/complexType>
         *                                       &lt;/element>
         *                                     &lt;/sequence>
         *                                   &lt;/restriction>
         *                                 &lt;/complexContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                             &lt;element name="appendItems" minOccurs="0">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;sequence>
         *                                       &lt;element name="appendItem">
         *                                         &lt;complexType>
         *                                           &lt;simpleContent>
         *                                             &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                                               &lt;attribute name="request" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                                             &lt;/extension>
         *                                           &lt;/simpleContent>
         *                                         &lt;/complexType>
         *                                       &lt;/element>
         *                                     &lt;/sequence>
         *                                   &lt;/restriction>
         *                                 &lt;/complexContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                             &lt;element name="intervalFormats" minOccurs="0">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;sequence>
         *                                       &lt;element name="intervalFormatFallback" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                                       &lt;element name="intervalFormatItem" maxOccurs="unbounded" minOccurs="0">
         *                                         &lt;complexType>
         *                                           &lt;complexContent>
         *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                               &lt;sequence>
         *                                                 &lt;element name="greatestDifference" maxOccurs="unbounded" minOccurs="0">
         *                                                   &lt;complexType>
         *                                                     &lt;simpleContent>
         *                                                       &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                                                         &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                                                         &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                                                       &lt;/extension>
         *                                                     &lt;/simpleContent>
         *                                                   &lt;/complexType>
         *                                                 &lt;/element>
         *                                               &lt;/sequence>
         *                                               &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                                             &lt;/restriction>
         *                                           &lt;/complexContent>
         *                                         &lt;/complexType>
         *                                       &lt;/element>
         *                                     &lt;/sequence>
         *                                   &lt;/restriction>
         *                                 &lt;/complexContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
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
            "calendar"
        })
        public static class Calendars {

            protected List<Ldml.Dates.Calendars.Calendar> calendar;

            /**
             * Gets the value of the calendar property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the calendar property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getCalendar().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Ldml.Dates.Calendars.Calendar }
             * 
             * 
             */
            public List<Ldml.Dates.Calendars.Calendar> getCalendar() {
                if (calendar == null) {
                    calendar = new ArrayList<Ldml.Dates.Calendars.Calendar>();
                }
                return this.calendar;
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
             *         &lt;element name="months" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="monthContext" maxOccurs="unbounded" minOccurs="0">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;sequence>
             *                             &lt;element name="monthWidth" maxOccurs="unbounded" minOccurs="0">
             *                               &lt;complexType>
             *                                 &lt;complexContent>
             *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                                     &lt;sequence>
             *                                       &lt;element name="month" maxOccurs="unbounded" minOccurs="0">
             *                                         &lt;complexType>
             *                                           &lt;simpleContent>
             *                                             &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *                                               &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}byte" />
             *                                             &lt;/extension>
             *                                           &lt;/simpleContent>
             *                                         &lt;/complexType>
             *                                       &lt;/element>
             *                                     &lt;/sequence>
             *                                     &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
             *                                   &lt;/restriction>
             *                                 &lt;/complexContent>
             *                               &lt;/complexType>
             *                             &lt;/element>
             *                           &lt;/sequence>
             *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="days" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="dayContext" maxOccurs="unbounded" minOccurs="0">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;sequence>
             *                             &lt;element name="dayWidth" maxOccurs="unbounded" minOccurs="0">
             *                               &lt;complexType>
             *                                 &lt;complexContent>
             *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                                     &lt;sequence>
             *                                       &lt;element name="day" maxOccurs="unbounded" minOccurs="0">
             *                                         &lt;complexType>
             *                                           &lt;simpleContent>
             *                                             &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *                                               &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
             *                                             &lt;/extension>
             *                                           &lt;/simpleContent>
             *                                         &lt;/complexType>
             *                                       &lt;/element>
             *                                     &lt;/sequence>
             *                                     &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
             *                                   &lt;/restriction>
             *                                 &lt;/complexContent>
             *                               &lt;/complexType>
             *                             &lt;/element>
             *                           &lt;/sequence>
             *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="quarters" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="quarterContext" maxOccurs="unbounded" minOccurs="0">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;sequence>
             *                             &lt;element name="quarterWidth" maxOccurs="unbounded" minOccurs="0">
             *                               &lt;complexType>
             *                                 &lt;complexContent>
             *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                                     &lt;sequence>
             *                                       &lt;element name="quarter" maxOccurs="unbounded" minOccurs="0">
             *                                         &lt;complexType>
             *                                           &lt;simpleContent>
             *                                             &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *                                               &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}byte" />
             *                                             &lt;/extension>
             *                                           &lt;/simpleContent>
             *                                         &lt;/complexType>
             *                                       &lt;/element>
             *                                     &lt;/sequence>
             *                                     &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
             *                                   &lt;/restriction>
             *                                 &lt;/complexContent>
             *                               &lt;/complexType>
             *                             &lt;/element>
             *                           &lt;/sequence>
             *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="dayPeriods" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="dayPeriodContext">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;sequence>
             *                             &lt;element name="dayPeriodWidth" maxOccurs="unbounded" minOccurs="0">
             *                               &lt;complexType>
             *                                 &lt;complexContent>
             *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                                     &lt;sequence>
             *                                       &lt;element name="dayPeriod" maxOccurs="unbounded" minOccurs="0">
             *                                         &lt;complexType>
             *                                           &lt;simpleContent>
             *                                             &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *                                               &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
             *                                             &lt;/extension>
             *                                           &lt;/simpleContent>
             *                                         &lt;/complexType>
             *                                       &lt;/element>
             *                                     &lt;/sequence>
             *                                     &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
             *                                   &lt;/restriction>
             *                                 &lt;/complexContent>
             *                               &lt;/complexType>
             *                             &lt;/element>
             *                           &lt;/sequence>
             *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="eras" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="eraNames" minOccurs="0">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;sequence>
             *                             &lt;element name="era" maxOccurs="unbounded" minOccurs="0">
             *                               &lt;complexType>
             *                                 &lt;simpleContent>
             *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *                                     &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}byte" />
             *                                     &lt;attribute name="alt" type="{http://www.w3.org/2001/XMLSchema}string" />
             *                                   &lt;/extension>
             *                                 &lt;/simpleContent>
             *                               &lt;/complexType>
             *                             &lt;/element>
             *                           &lt;/sequence>
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                   &lt;element name="eraAbbr">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;sequence>
             *                             &lt;element name="era" maxOccurs="unbounded" minOccurs="0">
             *                               &lt;complexType>
             *                                 &lt;simpleContent>
             *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *                                     &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}byte" />
             *                                     &lt;attribute name="alt" type="{http://www.w3.org/2001/XMLSchema}string" />
             *                                     &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
             *                                   &lt;/extension>
             *                                 &lt;/simpleContent>
             *                               &lt;/complexType>
             *                             &lt;/element>
             *                           &lt;/sequence>
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="dateFormats" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="dateFormatLength" maxOccurs="unbounded" minOccurs="0">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;sequence>
             *                             &lt;element name="dateFormat">
             *                               &lt;complexType>
             *                                 &lt;complexContent>
             *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                                     &lt;sequence>
             *                                       &lt;element name="pattern">
             *                                         &lt;complexType>
             *                                           &lt;simpleContent>
             *                                             &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *                                               &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
             *                                             &lt;/extension>
             *                                           &lt;/simpleContent>
             *                                         &lt;/complexType>
             *                                       &lt;/element>
             *                                     &lt;/sequence>
             *                                   &lt;/restriction>
             *                                 &lt;/complexContent>
             *                               &lt;/complexType>
             *                             &lt;/element>
             *                           &lt;/sequence>
             *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="timeFormats" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="timeFormatLength" maxOccurs="unbounded" minOccurs="0">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;sequence>
             *                             &lt;element name="timeFormat">
             *                               &lt;complexType>
             *                                 &lt;complexContent>
             *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                                     &lt;sequence>
             *                                       &lt;element name="pattern" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                                     &lt;/sequence>
             *                                   &lt;/restriction>
             *                                 &lt;/complexContent>
             *                               &lt;/complexType>
             *                             &lt;/element>
             *                           &lt;/sequence>
             *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="dateTimeFormats" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="dateTimeFormatLength" maxOccurs="unbounded" minOccurs="0">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;sequence>
             *                             &lt;element name="dateTimeFormat">
             *                               &lt;complexType>
             *                                 &lt;complexContent>
             *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                                     &lt;sequence>
             *                                       &lt;element name="pattern" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                                     &lt;/sequence>
             *                                   &lt;/restriction>
             *                                 &lt;/complexContent>
             *                               &lt;/complexType>
             *                             &lt;/element>
             *                           &lt;/sequence>
             *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                   &lt;element name="availableFormats">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;sequence>
             *                             &lt;element name="dateFormatItem" maxOccurs="unbounded" minOccurs="0">
             *                               &lt;complexType>
             *                                 &lt;simpleContent>
             *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *                                     &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
             *                                     &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
             *                                   &lt;/extension>
             *                                 &lt;/simpleContent>
             *                               &lt;/complexType>
             *                             &lt;/element>
             *                           &lt;/sequence>
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                   &lt;element name="appendItems" minOccurs="0">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;sequence>
             *                             &lt;element name="appendItem">
             *                               &lt;complexType>
             *                                 &lt;simpleContent>
             *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *                                     &lt;attribute name="request" type="{http://www.w3.org/2001/XMLSchema}string" />
             *                                   &lt;/extension>
             *                                 &lt;/simpleContent>
             *                               &lt;/complexType>
             *                             &lt;/element>
             *                           &lt;/sequence>
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                   &lt;element name="intervalFormats" minOccurs="0">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;sequence>
             *                             &lt;element name="intervalFormatFallback" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                             &lt;element name="intervalFormatItem" maxOccurs="unbounded" minOccurs="0">
             *                               &lt;complexType>
             *                                 &lt;complexContent>
             *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                                     &lt;sequence>
             *                                       &lt;element name="greatestDifference" maxOccurs="unbounded" minOccurs="0">
             *                                         &lt;complexType>
             *                                           &lt;simpleContent>
             *                                             &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *                                               &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
             *                                               &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
             *                                             &lt;/extension>
             *                                           &lt;/simpleContent>
             *                                         &lt;/complexType>
             *                                       &lt;/element>
             *                                     &lt;/sequence>
             *                                     &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
             *                                   &lt;/restriction>
             *                                 &lt;/complexContent>
             *                               &lt;/complexType>
             *                             &lt;/element>
             *                           &lt;/sequence>
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *       &lt;/sequence>
             *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "months",
                "days",
                "quarters",
                "dayPeriods",
                "eras",
                "dateFormats",
                "timeFormats",
                "dateTimeFormats"
            })
            public static class Calendar {

                protected Ldml.Dates.Calendars.Calendar.Months months;
                protected Ldml.Dates.Calendars.Calendar.Days days;
                protected Ldml.Dates.Calendars.Calendar.Quarters quarters;
                protected Ldml.Dates.Calendars.Calendar.DayPeriods dayPeriods;
                protected Ldml.Dates.Calendars.Calendar.Eras eras;
                protected Ldml.Dates.Calendars.Calendar.DateFormats dateFormats;
                protected Ldml.Dates.Calendars.Calendar.TimeFormats timeFormats;
                protected Ldml.Dates.Calendars.Calendar.DateTimeFormats dateTimeFormats;
                @XmlAttribute(name = "type")
                protected String type;

                /**
                 * Obtiene el valor de la propiedad months.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Ldml.Dates.Calendars.Calendar.Months }
                 *     
                 */
                public Ldml.Dates.Calendars.Calendar.Months getMonths() {
                    return months;
                }

                /**
                 * Define el valor de la propiedad months.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Ldml.Dates.Calendars.Calendar.Months }
                 *     
                 */
                public void setMonths(Ldml.Dates.Calendars.Calendar.Months value) {
                    this.months = value;
                }

                /**
                 * Obtiene el valor de la propiedad days.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Ldml.Dates.Calendars.Calendar.Days }
                 *     
                 */
                public Ldml.Dates.Calendars.Calendar.Days getDays() {
                    return days;
                }

                /**
                 * Define el valor de la propiedad days.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Ldml.Dates.Calendars.Calendar.Days }
                 *     
                 */
                public void setDays(Ldml.Dates.Calendars.Calendar.Days value) {
                    this.days = value;
                }

                /**
                 * Obtiene el valor de la propiedad quarters.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Ldml.Dates.Calendars.Calendar.Quarters }
                 *     
                 */
                public Ldml.Dates.Calendars.Calendar.Quarters getQuarters() {
                    return quarters;
                }

                /**
                 * Define el valor de la propiedad quarters.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Ldml.Dates.Calendars.Calendar.Quarters }
                 *     
                 */
                public void setQuarters(Ldml.Dates.Calendars.Calendar.Quarters value) {
                    this.quarters = value;
                }

                /**
                 * Obtiene el valor de la propiedad dayPeriods.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Ldml.Dates.Calendars.Calendar.DayPeriods }
                 *     
                 */
                public Ldml.Dates.Calendars.Calendar.DayPeriods getDayPeriods() {
                    return dayPeriods;
                }

                /**
                 * Define el valor de la propiedad dayPeriods.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Ldml.Dates.Calendars.Calendar.DayPeriods }
                 *     
                 */
                public void setDayPeriods(Ldml.Dates.Calendars.Calendar.DayPeriods value) {
                    this.dayPeriods = value;
                }

                /**
                 * Obtiene el valor de la propiedad eras.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Ldml.Dates.Calendars.Calendar.Eras }
                 *     
                 */
                public Ldml.Dates.Calendars.Calendar.Eras getEras() {
                    return eras;
                }

                /**
                 * Define el valor de la propiedad eras.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Ldml.Dates.Calendars.Calendar.Eras }
                 *     
                 */
                public void setEras(Ldml.Dates.Calendars.Calendar.Eras value) {
                    this.eras = value;
                }

                /**
                 * Obtiene el valor de la propiedad dateFormats.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Ldml.Dates.Calendars.Calendar.DateFormats }
                 *     
                 */
                public Ldml.Dates.Calendars.Calendar.DateFormats getDateFormats() {
                    return dateFormats;
                }

                /**
                 * Define el valor de la propiedad dateFormats.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Ldml.Dates.Calendars.Calendar.DateFormats }
                 *     
                 */
                public void setDateFormats(Ldml.Dates.Calendars.Calendar.DateFormats value) {
                    this.dateFormats = value;
                }

                /**
                 * Obtiene el valor de la propiedad timeFormats.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Ldml.Dates.Calendars.Calendar.TimeFormats }
                 *     
                 */
                public Ldml.Dates.Calendars.Calendar.TimeFormats getTimeFormats() {
                    return timeFormats;
                }

                /**
                 * Define el valor de la propiedad timeFormats.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Ldml.Dates.Calendars.Calendar.TimeFormats }
                 *     
                 */
                public void setTimeFormats(Ldml.Dates.Calendars.Calendar.TimeFormats value) {
                    this.timeFormats = value;
                }

                /**
                 * Obtiene el valor de la propiedad dateTimeFormats.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Ldml.Dates.Calendars.Calendar.DateTimeFormats }
                 *     
                 */
                public Ldml.Dates.Calendars.Calendar.DateTimeFormats getDateTimeFormats() {
                    return dateTimeFormats;
                }

                /**
                 * Define el valor de la propiedad dateTimeFormats.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Ldml.Dates.Calendars.Calendar.DateTimeFormats }
                 *     
                 */
                public void setDateTimeFormats(Ldml.Dates.Calendars.Calendar.DateTimeFormats value) {
                    this.dateTimeFormats = value;
                }

                /**
                 * Obtiene el valor de la propiedad type.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getType() {
                    return type;
                }

                /**
                 * Define el valor de la propiedad type.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setType(String value) {
                    this.type = value;
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
                 *         &lt;element name="dateFormatLength" maxOccurs="unbounded" minOccurs="0">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;sequence>
                 *                   &lt;element name="dateFormat">
                 *                     &lt;complexType>
                 *                       &lt;complexContent>
                 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                           &lt;sequence>
                 *                             &lt;element name="pattern">
                 *                               &lt;complexType>
                 *                                 &lt;simpleContent>
                 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                 *                                     &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
                 *                                   &lt;/extension>
                 *                                 &lt;/simpleContent>
                 *                               &lt;/complexType>
                 *                             &lt;/element>
                 *                           &lt;/sequence>
                 *                         &lt;/restriction>
                 *                       &lt;/complexContent>
                 *                     &lt;/complexType>
                 *                   &lt;/element>
                 *                 &lt;/sequence>
                 *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
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
                    "dateFormatLength"
                })
                public static class DateFormats {

                    protected List<Ldml.Dates.Calendars.Calendar.DateFormats.DateFormatLength> dateFormatLength;

                    /**
                     * Gets the value of the dateFormatLength property.
                     * 
                     * <p>
                     * This accessor method returns a reference to the live list,
                     * not a snapshot. Therefore any modification you make to the
                     * returned list will be present inside the JAXB object.
                     * This is why there is not a <CODE>set</CODE> method for the dateFormatLength property.
                     * 
                     * <p>
                     * For example, to add a new item, do as follows:
                     * <pre>
                     *    getDateFormatLength().add(newItem);
                     * </pre>
                     * 
                     * 
                     * <p>
                     * Objects of the following type(s) are allowed in the list
                     * {@link Ldml.Dates.Calendars.Calendar.DateFormats.DateFormatLength }
                     * 
                     * 
                     */
                    public List<Ldml.Dates.Calendars.Calendar.DateFormats.DateFormatLength> getDateFormatLength() {
                        if (dateFormatLength == null) {
                            dateFormatLength = new ArrayList<Ldml.Dates.Calendars.Calendar.DateFormats.DateFormatLength>();
                        }
                        return this.dateFormatLength;
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
                     *         &lt;element name="dateFormat">
                     *           &lt;complexType>
                     *             &lt;complexContent>
                     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *                 &lt;sequence>
                     *                   &lt;element name="pattern">
                     *                     &lt;complexType>
                     *                       &lt;simpleContent>
                     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                     *                           &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
                     *                         &lt;/extension>
                     *                       &lt;/simpleContent>
                     *                     &lt;/complexType>
                     *                   &lt;/element>
                     *                 &lt;/sequence>
                     *               &lt;/restriction>
                     *             &lt;/complexContent>
                     *           &lt;/complexType>
                     *         &lt;/element>
                     *       &lt;/sequence>
                     *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
                     *     &lt;/restriction>
                     *   &lt;/complexContent>
                     * &lt;/complexType>
                     * </pre>
                     * 
                     * 
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                        "dateFormat"
                    })
                    public static class DateFormatLength {

                        @XmlElement(required = true)
                        protected Ldml.Dates.Calendars.Calendar.DateFormats.DateFormatLength.DateFormat dateFormat;
                        @XmlAttribute(name = "type")
                        protected String type;

                        /**
                         * Obtiene el valor de la propiedad dateFormat.
                         * 
                         * @return
                         *     possible object is
                         *     {@link Ldml.Dates.Calendars.Calendar.DateFormats.DateFormatLength.DateFormat }
                         *     
                         */
                        public Ldml.Dates.Calendars.Calendar.DateFormats.DateFormatLength.DateFormat getDateFormat() {
                            return dateFormat;
                        }

                        /**
                         * Define el valor de la propiedad dateFormat.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link Ldml.Dates.Calendars.Calendar.DateFormats.DateFormatLength.DateFormat }
                         *     
                         */
                        public void setDateFormat(Ldml.Dates.Calendars.Calendar.DateFormats.DateFormatLength.DateFormat value) {
                            this.dateFormat = value;
                        }

                        /**
                         * Obtiene el valor de la propiedad type.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getType() {
                            return type;
                        }

                        /**
                         * Define el valor de la propiedad type.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setType(String value) {
                            this.type = value;
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
                         *         &lt;element name="pattern">
                         *           &lt;complexType>
                         *             &lt;simpleContent>
                         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                         *                 &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
                         *               &lt;/extension>
                         *             &lt;/simpleContent>
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
                            "pattern"
                        })
                        public static class DateFormat {

                            @XmlElement(required = true)
                            protected Ldml.Dates.Calendars.Calendar.DateFormats.DateFormatLength.DateFormat.Pattern pattern;

                            /**
                             * Obtiene el valor de la propiedad pattern.
                             * 
                             * @return
                             *     possible object is
                             *     {@link Ldml.Dates.Calendars.Calendar.DateFormats.DateFormatLength.DateFormat.Pattern }
                             *     
                             */
                            public Ldml.Dates.Calendars.Calendar.DateFormats.DateFormatLength.DateFormat.Pattern getPattern() {
                                return pattern;
                            }

                            /**
                             * Define el valor de la propiedad pattern.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link Ldml.Dates.Calendars.Calendar.DateFormats.DateFormatLength.DateFormat.Pattern }
                             *     
                             */
                            public void setPattern(Ldml.Dates.Calendars.Calendar.DateFormats.DateFormatLength.DateFormat.Pattern value) {
                                this.pattern = value;
                            }


                            /**
                             * <p>Clase Java para anonymous complex type.
                             * 
                             * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
                             * 
                             * <pre>
                             * &lt;complexType>
                             *   &lt;simpleContent>
                             *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                             *       &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
                             *     &lt;/extension>
                             *   &lt;/simpleContent>
                             * &lt;/complexType>
                             * </pre>
                             * 
                             * 
                             */
                            @XmlAccessorType(XmlAccessType.FIELD)
                            @XmlType(name = "", propOrder = {
                                "value"
                            })
                            public static class Pattern {

                                @XmlValue
                                protected String value;
                                @XmlAttribute(name = "draft")
                                protected String draft;

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

                                /**
                                 * Obtiene el valor de la propiedad draft.
                                 * 
                                 * @return
                                 *     possible object is
                                 *     {@link String }
                                 *     
                                 */
                                public String getDraft() {
                                    return draft;
                                }

                                /**
                                 * Define el valor de la propiedad draft.
                                 * 
                                 * @param value
                                 *     allowed object is
                                 *     {@link String }
                                 *     
                                 */
                                public void setDraft(String value) {
                                    this.draft = value;
                                }

                            }

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
                 *         &lt;element name="dateTimeFormatLength" maxOccurs="unbounded" minOccurs="0">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;sequence>
                 *                   &lt;element name="dateTimeFormat">
                 *                     &lt;complexType>
                 *                       &lt;complexContent>
                 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                           &lt;sequence>
                 *                             &lt;element name="pattern" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *                           &lt;/sequence>
                 *                         &lt;/restriction>
                 *                       &lt;/complexContent>
                 *                     &lt;/complexType>
                 *                   &lt;/element>
                 *                 &lt;/sequence>
                 *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
                 *               &lt;/restriction>
                 *             &lt;/complexContent>
                 *           &lt;/complexType>
                 *         &lt;/element>
                 *         &lt;element name="availableFormats">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;sequence>
                 *                   &lt;element name="dateFormatItem" maxOccurs="unbounded" minOccurs="0">
                 *                     &lt;complexType>
                 *                       &lt;simpleContent>
                 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                 *                           &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
                 *                           &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
                 *                         &lt;/extension>
                 *                       &lt;/simpleContent>
                 *                     &lt;/complexType>
                 *                   &lt;/element>
                 *                 &lt;/sequence>
                 *               &lt;/restriction>
                 *             &lt;/complexContent>
                 *           &lt;/complexType>
                 *         &lt;/element>
                 *         &lt;element name="appendItems" minOccurs="0">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;sequence>
                 *                   &lt;element name="appendItem">
                 *                     &lt;complexType>
                 *                       &lt;simpleContent>
                 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                 *                           &lt;attribute name="request" type="{http://www.w3.org/2001/XMLSchema}string" />
                 *                         &lt;/extension>
                 *                       &lt;/simpleContent>
                 *                     &lt;/complexType>
                 *                   &lt;/element>
                 *                 &lt;/sequence>
                 *               &lt;/restriction>
                 *             &lt;/complexContent>
                 *           &lt;/complexType>
                 *         &lt;/element>
                 *         &lt;element name="intervalFormats" minOccurs="0">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;sequence>
                 *                   &lt;element name="intervalFormatFallback" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *                   &lt;element name="intervalFormatItem" maxOccurs="unbounded" minOccurs="0">
                 *                     &lt;complexType>
                 *                       &lt;complexContent>
                 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                           &lt;sequence>
                 *                             &lt;element name="greatestDifference" maxOccurs="unbounded" minOccurs="0">
                 *                               &lt;complexType>
                 *                                 &lt;simpleContent>
                 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                 *                                     &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
                 *                                     &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
                 *                                   &lt;/extension>
                 *                                 &lt;/simpleContent>
                 *                               &lt;/complexType>
                 *                             &lt;/element>
                 *                           &lt;/sequence>
                 *                           &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
                 *                         &lt;/restriction>
                 *                       &lt;/complexContent>
                 *                     &lt;/complexType>
                 *                   &lt;/element>
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
                    "dateTimeFormatLength",
                    "availableFormats",
                    "appendItems",
                    "intervalFormats"
                })
                public static class DateTimeFormats {

                    protected List<Ldml.Dates.Calendars.Calendar.DateTimeFormats.DateTimeFormatLength> dateTimeFormatLength;
                    @XmlElement(required = true)
                    protected Ldml.Dates.Calendars.Calendar.DateTimeFormats.AvailableFormats availableFormats;
                    protected Ldml.Dates.Calendars.Calendar.DateTimeFormats.AppendItems appendItems;
                    protected Ldml.Dates.Calendars.Calendar.DateTimeFormats.IntervalFormats intervalFormats;

                    /**
                     * Gets the value of the dateTimeFormatLength property.
                     * 
                     * <p>
                     * This accessor method returns a reference to the live list,
                     * not a snapshot. Therefore any modification you make to the
                     * returned list will be present inside the JAXB object.
                     * This is why there is not a <CODE>set</CODE> method for the dateTimeFormatLength property.
                     * 
                     * <p>
                     * For example, to add a new item, do as follows:
                     * <pre>
                     *    getDateTimeFormatLength().add(newItem);
                     * </pre>
                     * 
                     * 
                     * <p>
                     * Objects of the following type(s) are allowed in the list
                     * {@link Ldml.Dates.Calendars.Calendar.DateTimeFormats.DateTimeFormatLength }
                     * 
                     * 
                     */
                    public List<Ldml.Dates.Calendars.Calendar.DateTimeFormats.DateTimeFormatLength> getDateTimeFormatLength() {
                        if (dateTimeFormatLength == null) {
                            dateTimeFormatLength = new ArrayList<Ldml.Dates.Calendars.Calendar.DateTimeFormats.DateTimeFormatLength>();
                        }
                        return this.dateTimeFormatLength;
                    }

                    /**
                     * Obtiene el valor de la propiedad availableFormats.
                     * 
                     * @return
                     *     possible object is
                     *     {@link Ldml.Dates.Calendars.Calendar.DateTimeFormats.AvailableFormats }
                     *     
                     */
                    public Ldml.Dates.Calendars.Calendar.DateTimeFormats.AvailableFormats getAvailableFormats() {
                        return availableFormats;
                    }

                    /**
                     * Define el valor de la propiedad availableFormats.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link Ldml.Dates.Calendars.Calendar.DateTimeFormats.AvailableFormats }
                     *     
                     */
                    public void setAvailableFormats(Ldml.Dates.Calendars.Calendar.DateTimeFormats.AvailableFormats value) {
                        this.availableFormats = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad appendItems.
                     * 
                     * @return
                     *     possible object is
                     *     {@link Ldml.Dates.Calendars.Calendar.DateTimeFormats.AppendItems }
                     *     
                     */
                    public Ldml.Dates.Calendars.Calendar.DateTimeFormats.AppendItems getAppendItems() {
                        return appendItems;
                    }

                    /**
                     * Define el valor de la propiedad appendItems.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link Ldml.Dates.Calendars.Calendar.DateTimeFormats.AppendItems }
                     *     
                     */
                    public void setAppendItems(Ldml.Dates.Calendars.Calendar.DateTimeFormats.AppendItems value) {
                        this.appendItems = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad intervalFormats.
                     * 
                     * @return
                     *     possible object is
                     *     {@link Ldml.Dates.Calendars.Calendar.DateTimeFormats.IntervalFormats }
                     *     
                     */
                    public Ldml.Dates.Calendars.Calendar.DateTimeFormats.IntervalFormats getIntervalFormats() {
                        return intervalFormats;
                    }

                    /**
                     * Define el valor de la propiedad intervalFormats.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link Ldml.Dates.Calendars.Calendar.DateTimeFormats.IntervalFormats }
                     *     
                     */
                    public void setIntervalFormats(Ldml.Dates.Calendars.Calendar.DateTimeFormats.IntervalFormats value) {
                        this.intervalFormats = value;
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
                     *         &lt;element name="appendItem">
                     *           &lt;complexType>
                     *             &lt;simpleContent>
                     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                     *                 &lt;attribute name="request" type="{http://www.w3.org/2001/XMLSchema}string" />
                     *               &lt;/extension>
                     *             &lt;/simpleContent>
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
                        "appendItem"
                    })
                    public static class AppendItems {

                        @XmlElement(required = true)
                        protected Ldml.Dates.Calendars.Calendar.DateTimeFormats.AppendItems.AppendItem appendItem;

                        /**
                         * Obtiene el valor de la propiedad appendItem.
                         * 
                         * @return
                         *     possible object is
                         *     {@link Ldml.Dates.Calendars.Calendar.DateTimeFormats.AppendItems.AppendItem }
                         *     
                         */
                        public Ldml.Dates.Calendars.Calendar.DateTimeFormats.AppendItems.AppendItem getAppendItem() {
                            return appendItem;
                        }

                        /**
                         * Define el valor de la propiedad appendItem.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link Ldml.Dates.Calendars.Calendar.DateTimeFormats.AppendItems.AppendItem }
                         *     
                         */
                        public void setAppendItem(Ldml.Dates.Calendars.Calendar.DateTimeFormats.AppendItems.AppendItem value) {
                            this.appendItem = value;
                        }


                        /**
                         * <p>Clase Java para anonymous complex type.
                         * 
                         * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
                         * 
                         * <pre>
                         * &lt;complexType>
                         *   &lt;simpleContent>
                         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                         *       &lt;attribute name="request" type="{http://www.w3.org/2001/XMLSchema}string" />
                         *     &lt;/extension>
                         *   &lt;/simpleContent>
                         * &lt;/complexType>
                         * </pre>
                         * 
                         * 
                         */
                        @XmlAccessorType(XmlAccessType.FIELD)
                        @XmlType(name = "", propOrder = {
                            "value"
                        })
                        public static class AppendItem {

                            @XmlValue
                            protected String value;
                            @XmlAttribute(name = "request")
                            protected String request;

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

                            /**
                             * Obtiene el valor de la propiedad request.
                             * 
                             * @return
                             *     possible object is
                             *     {@link String }
                             *     
                             */
                            public String getRequest() {
                                return request;
                            }

                            /**
                             * Define el valor de la propiedad request.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *     
                             */
                            public void setRequest(String value) {
                                this.request = value;
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
                     *         &lt;element name="dateFormatItem" maxOccurs="unbounded" minOccurs="0">
                     *           &lt;complexType>
                     *             &lt;simpleContent>
                     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                     *                 &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
                     *                 &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
                     *               &lt;/extension>
                     *             &lt;/simpleContent>
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
                        "dateFormatItem"
                    })
                    public static class AvailableFormats {

                        protected List<Ldml.Dates.Calendars.Calendar.DateTimeFormats.AvailableFormats.DateFormatItem> dateFormatItem;

                        /**
                         * Gets the value of the dateFormatItem property.
                         * 
                         * <p>
                         * This accessor method returns a reference to the live list,
                         * not a snapshot. Therefore any modification you make to the
                         * returned list will be present inside the JAXB object.
                         * This is why there is not a <CODE>set</CODE> method for the dateFormatItem property.
                         * 
                         * <p>
                         * For example, to add a new item, do as follows:
                         * <pre>
                         *    getDateFormatItem().add(newItem);
                         * </pre>
                         * 
                         * 
                         * <p>
                         * Objects of the following type(s) are allowed in the list
                         * {@link Ldml.Dates.Calendars.Calendar.DateTimeFormats.AvailableFormats.DateFormatItem }
                         * 
                         * 
                         */
                        public List<Ldml.Dates.Calendars.Calendar.DateTimeFormats.AvailableFormats.DateFormatItem> getDateFormatItem() {
                            if (dateFormatItem == null) {
                                dateFormatItem = new ArrayList<Ldml.Dates.Calendars.Calendar.DateTimeFormats.AvailableFormats.DateFormatItem>();
                            }
                            return this.dateFormatItem;
                        }


                        /**
                         * <p>Clase Java para anonymous complex type.
                         * 
                         * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
                         * 
                         * <pre>
                         * &lt;complexType>
                         *   &lt;simpleContent>
                         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                         *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
                         *       &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
                         *     &lt;/extension>
                         *   &lt;/simpleContent>
                         * &lt;/complexType>
                         * </pre>
                         * 
                         * 
                         */
                        @XmlAccessorType(XmlAccessType.FIELD)
                        @XmlType(name = "", propOrder = {
                            "value"
                        })
                        public static class DateFormatItem {

                            @XmlValue
                            protected String value;
                            @XmlAttribute(name = "id")
                            protected String id;
                            @XmlAttribute(name = "draft")
                            protected String draft;

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

                            /**
                             * Obtiene el valor de la propiedad id.
                             * 
                             * @return
                             *     possible object is
                             *     {@link String }
                             *     
                             */
                            public String getId() {
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
                            public void setId(String value) {
                                this.id = value;
                            }

                            /**
                             * Obtiene el valor de la propiedad draft.
                             * 
                             * @return
                             *     possible object is
                             *     {@link String }
                             *     
                             */
                            public String getDraft() {
                                return draft;
                            }

                            /**
                             * Define el valor de la propiedad draft.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *     
                             */
                            public void setDraft(String value) {
                                this.draft = value;
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
                     *         &lt;element name="dateTimeFormat">
                     *           &lt;complexType>
                     *             &lt;complexContent>
                     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *                 &lt;sequence>
                     *                   &lt;element name="pattern" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *                 &lt;/sequence>
                     *               &lt;/restriction>
                     *             &lt;/complexContent>
                     *           &lt;/complexType>
                     *         &lt;/element>
                     *       &lt;/sequence>
                     *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
                     *     &lt;/restriction>
                     *   &lt;/complexContent>
                     * &lt;/complexType>
                     * </pre>
                     * 
                     * 
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                        "dateTimeFormat"
                    })
                    public static class DateTimeFormatLength {

                        @XmlElement(required = true)
                        protected Ldml.Dates.Calendars.Calendar.DateTimeFormats.DateTimeFormatLength.DateTimeFormat dateTimeFormat;
                        @XmlAttribute(name = "type")
                        protected String type;

                        /**
                         * Obtiene el valor de la propiedad dateTimeFormat.
                         * 
                         * @return
                         *     possible object is
                         *     {@link Ldml.Dates.Calendars.Calendar.DateTimeFormats.DateTimeFormatLength.DateTimeFormat }
                         *     
                         */
                        public Ldml.Dates.Calendars.Calendar.DateTimeFormats.DateTimeFormatLength.DateTimeFormat getDateTimeFormat() {
                            return dateTimeFormat;
                        }

                        /**
                         * Define el valor de la propiedad dateTimeFormat.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link Ldml.Dates.Calendars.Calendar.DateTimeFormats.DateTimeFormatLength.DateTimeFormat }
                         *     
                         */
                        public void setDateTimeFormat(Ldml.Dates.Calendars.Calendar.DateTimeFormats.DateTimeFormatLength.DateTimeFormat value) {
                            this.dateTimeFormat = value;
                        }

                        /**
                         * Obtiene el valor de la propiedad type.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getType() {
                            return type;
                        }

                        /**
                         * Define el valor de la propiedad type.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setType(String value) {
                            this.type = value;
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
                         *         &lt;element name="pattern" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
                            "pattern"
                        })
                        public static class DateTimeFormat {

                            @XmlElement(required = true)
                            protected String pattern;

                            /**
                             * Obtiene el valor de la propiedad pattern.
                             * 
                             * @return
                             *     possible object is
                             *     {@link String }
                             *     
                             */
                            public String getPattern() {
                                return pattern;
                            }

                            /**
                             * Define el valor de la propiedad pattern.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *     
                             */
                            public void setPattern(String value) {
                                this.pattern = value;
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
                     *         &lt;element name="intervalFormatFallback" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *         &lt;element name="intervalFormatItem" maxOccurs="unbounded" minOccurs="0">
                     *           &lt;complexType>
                     *             &lt;complexContent>
                     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *                 &lt;sequence>
                     *                   &lt;element name="greatestDifference" maxOccurs="unbounded" minOccurs="0">
                     *                     &lt;complexType>
                     *                       &lt;simpleContent>
                     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                     *                           &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
                     *                           &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
                     *                         &lt;/extension>
                     *                       &lt;/simpleContent>
                     *                     &lt;/complexType>
                     *                   &lt;/element>
                     *                 &lt;/sequence>
                     *                 &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
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
                        "intervalFormatFallback",
                        "intervalFormatItem"
                    })
                    public static class IntervalFormats {

                        @XmlElement(required = true)
                        protected String intervalFormatFallback;
                        protected List<Ldml.Dates.Calendars.Calendar.DateTimeFormats.IntervalFormats.IntervalFormatItem> intervalFormatItem;

                        /**
                         * Obtiene el valor de la propiedad intervalFormatFallback.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getIntervalFormatFallback() {
                            return intervalFormatFallback;
                        }

                        /**
                         * Define el valor de la propiedad intervalFormatFallback.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setIntervalFormatFallback(String value) {
                            this.intervalFormatFallback = value;
                        }

                        /**
                         * Gets the value of the intervalFormatItem property.
                         * 
                         * <p>
                         * This accessor method returns a reference to the live list,
                         * not a snapshot. Therefore any modification you make to the
                         * returned list will be present inside the JAXB object.
                         * This is why there is not a <CODE>set</CODE> method for the intervalFormatItem property.
                         * 
                         * <p>
                         * For example, to add a new item, do as follows:
                         * <pre>
                         *    getIntervalFormatItem().add(newItem);
                         * </pre>
                         * 
                         * 
                         * <p>
                         * Objects of the following type(s) are allowed in the list
                         * {@link Ldml.Dates.Calendars.Calendar.DateTimeFormats.IntervalFormats.IntervalFormatItem }
                         * 
                         * 
                         */
                        public List<Ldml.Dates.Calendars.Calendar.DateTimeFormats.IntervalFormats.IntervalFormatItem> getIntervalFormatItem() {
                            if (intervalFormatItem == null) {
                                intervalFormatItem = new ArrayList<Ldml.Dates.Calendars.Calendar.DateTimeFormats.IntervalFormats.IntervalFormatItem>();
                            }
                            return this.intervalFormatItem;
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
                         *         &lt;element name="greatestDifference" maxOccurs="unbounded" minOccurs="0">
                         *           &lt;complexType>
                         *             &lt;simpleContent>
                         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                         *                 &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
                         *                 &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
                         *               &lt;/extension>
                         *             &lt;/simpleContent>
                         *           &lt;/complexType>
                         *         &lt;/element>
                         *       &lt;/sequence>
                         *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
                         *     &lt;/restriction>
                         *   &lt;/complexContent>
                         * &lt;/complexType>
                         * </pre>
                         * 
                         * 
                         */
                        @XmlAccessorType(XmlAccessType.FIELD)
                        @XmlType(name = "", propOrder = {
                            "greatestDifference"
                        })
                        public static class IntervalFormatItem {

                            protected List<Ldml.Dates.Calendars.Calendar.DateTimeFormats.IntervalFormats.IntervalFormatItem.GreatestDifference> greatestDifference;
                            @XmlAttribute(name = "id")
                            protected String id;

                            /**
                             * Gets the value of the greatestDifference property.
                             * 
                             * <p>
                             * This accessor method returns a reference to the live list,
                             * not a snapshot. Therefore any modification you make to the
                             * returned list will be present inside the JAXB object.
                             * This is why there is not a <CODE>set</CODE> method for the greatestDifference property.
                             * 
                             * <p>
                             * For example, to add a new item, do as follows:
                             * <pre>
                             *    getGreatestDifference().add(newItem);
                             * </pre>
                             * 
                             * 
                             * <p>
                             * Objects of the following type(s) are allowed in the list
                             * {@link Ldml.Dates.Calendars.Calendar.DateTimeFormats.IntervalFormats.IntervalFormatItem.GreatestDifference }
                             * 
                             * 
                             */
                            public List<Ldml.Dates.Calendars.Calendar.DateTimeFormats.IntervalFormats.IntervalFormatItem.GreatestDifference> getGreatestDifference() {
                                if (greatestDifference == null) {
                                    greatestDifference = new ArrayList<Ldml.Dates.Calendars.Calendar.DateTimeFormats.IntervalFormats.IntervalFormatItem.GreatestDifference>();
                                }
                                return this.greatestDifference;
                            }

                            /**
                             * Obtiene el valor de la propiedad id.
                             * 
                             * @return
                             *     possible object is
                             *     {@link String }
                             *     
                             */
                            public String getId() {
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
                            public void setId(String value) {
                                this.id = value;
                            }


                            /**
                             * <p>Clase Java para anonymous complex type.
                             * 
                             * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
                             * 
                             * <pre>
                             * &lt;complexType>
                             *   &lt;simpleContent>
                             *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                             *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
                             *       &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
                             *     &lt;/extension>
                             *   &lt;/simpleContent>
                             * &lt;/complexType>
                             * </pre>
                             * 
                             * 
                             */
                            @XmlAccessorType(XmlAccessType.FIELD)
                            @XmlType(name = "", propOrder = {
                                "value"
                            })
                            public static class GreatestDifference {

                                @XmlValue
                                protected String value;
                                @XmlAttribute(name = "id")
                                protected String id;
                                @XmlAttribute(name = "draft")
                                protected String draft;

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

                                /**
                                 * Obtiene el valor de la propiedad id.
                                 * 
                                 * @return
                                 *     possible object is
                                 *     {@link String }
                                 *     
                                 */
                                public String getId() {
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
                                public void setId(String value) {
                                    this.id = value;
                                }

                                /**
                                 * Obtiene el valor de la propiedad draft.
                                 * 
                                 * @return
                                 *     possible object is
                                 *     {@link String }
                                 *     
                                 */
                                public String getDraft() {
                                    return draft;
                                }

                                /**
                                 * Define el valor de la propiedad draft.
                                 * 
                                 * @param value
                                 *     allowed object is
                                 *     {@link String }
                                 *     
                                 */
                                public void setDraft(String value) {
                                    this.draft = value;
                                }

                            }

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
                 *         &lt;element name="dayPeriodContext">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;sequence>
                 *                   &lt;element name="dayPeriodWidth" maxOccurs="unbounded" minOccurs="0">
                 *                     &lt;complexType>
                 *                       &lt;complexContent>
                 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                           &lt;sequence>
                 *                             &lt;element name="dayPeriod" maxOccurs="unbounded" minOccurs="0">
                 *                               &lt;complexType>
                 *                                 &lt;simpleContent>
                 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                 *                                     &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
                 *                                   &lt;/extension>
                 *                                 &lt;/simpleContent>
                 *                               &lt;/complexType>
                 *                             &lt;/element>
                 *                           &lt;/sequence>
                 *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
                 *                         &lt;/restriction>
                 *                       &lt;/complexContent>
                 *                     &lt;/complexType>
                 *                   &lt;/element>
                 *                 &lt;/sequence>
                 *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
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
                    "dayPeriodContext"
                })
                public static class DayPeriods {

                    @XmlElement(required = true)
                    protected Ldml.Dates.Calendars.Calendar.DayPeriods.DayPeriodContext dayPeriodContext;

                    /**
                     * Obtiene el valor de la propiedad dayPeriodContext.
                     * 
                     * @return
                     *     possible object is
                     *     {@link Ldml.Dates.Calendars.Calendar.DayPeriods.DayPeriodContext }
                     *     
                     */
                    public Ldml.Dates.Calendars.Calendar.DayPeriods.DayPeriodContext getDayPeriodContext() {
                        return dayPeriodContext;
                    }

                    /**
                     * Define el valor de la propiedad dayPeriodContext.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link Ldml.Dates.Calendars.Calendar.DayPeriods.DayPeriodContext }
                     *     
                     */
                    public void setDayPeriodContext(Ldml.Dates.Calendars.Calendar.DayPeriods.DayPeriodContext value) {
                        this.dayPeriodContext = value;
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
                     *         &lt;element name="dayPeriodWidth" maxOccurs="unbounded" minOccurs="0">
                     *           &lt;complexType>
                     *             &lt;complexContent>
                     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *                 &lt;sequence>
                     *                   &lt;element name="dayPeriod" maxOccurs="unbounded" minOccurs="0">
                     *                     &lt;complexType>
                     *                       &lt;simpleContent>
                     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                     *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
                     *                         &lt;/extension>
                     *                       &lt;/simpleContent>
                     *                     &lt;/complexType>
                     *                   &lt;/element>
                     *                 &lt;/sequence>
                     *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
                     *               &lt;/restriction>
                     *             &lt;/complexContent>
                     *           &lt;/complexType>
                     *         &lt;/element>
                     *       &lt;/sequence>
                     *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
                     *     &lt;/restriction>
                     *   &lt;/complexContent>
                     * &lt;/complexType>
                     * </pre>
                     * 
                     * 
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                        "dayPeriodWidth"
                    })
                    public static class DayPeriodContext {

                        protected List<Ldml.Dates.Calendars.Calendar.DayPeriods.DayPeriodContext.DayPeriodWidth> dayPeriodWidth;
                        @XmlAttribute(name = "type")
                        protected String type;

                        /**
                         * Gets the value of the dayPeriodWidth property.
                         * 
                         * <p>
                         * This accessor method returns a reference to the live list,
                         * not a snapshot. Therefore any modification you make to the
                         * returned list will be present inside the JAXB object.
                         * This is why there is not a <CODE>set</CODE> method for the dayPeriodWidth property.
                         * 
                         * <p>
                         * For example, to add a new item, do as follows:
                         * <pre>
                         *    getDayPeriodWidth().add(newItem);
                         * </pre>
                         * 
                         * 
                         * <p>
                         * Objects of the following type(s) are allowed in the list
                         * {@link Ldml.Dates.Calendars.Calendar.DayPeriods.DayPeriodContext.DayPeriodWidth }
                         * 
                         * 
                         */
                        public List<Ldml.Dates.Calendars.Calendar.DayPeriods.DayPeriodContext.DayPeriodWidth> getDayPeriodWidth() {
                            if (dayPeriodWidth == null) {
                                dayPeriodWidth = new ArrayList<Ldml.Dates.Calendars.Calendar.DayPeriods.DayPeriodContext.DayPeriodWidth>();
                            }
                            return this.dayPeriodWidth;
                        }

                        /**
                         * Obtiene el valor de la propiedad type.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getType() {
                            return type;
                        }

                        /**
                         * Define el valor de la propiedad type.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setType(String value) {
                            this.type = value;
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
                         *         &lt;element name="dayPeriod" maxOccurs="unbounded" minOccurs="0">
                         *           &lt;complexType>
                         *             &lt;simpleContent>
                         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                         *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
                         *               &lt;/extension>
                         *             &lt;/simpleContent>
                         *           &lt;/complexType>
                         *         &lt;/element>
                         *       &lt;/sequence>
                         *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
                         *     &lt;/restriction>
                         *   &lt;/complexContent>
                         * &lt;/complexType>
                         * </pre>
                         * 
                         * 
                         */
                        @XmlAccessorType(XmlAccessType.FIELD)
                        @XmlType(name = "", propOrder = {
                            "dayPeriod"
                        })
                        public static class DayPeriodWidth {

                            protected List<Ldml.Dates.Calendars.Calendar.DayPeriods.DayPeriodContext.DayPeriodWidth.DayPeriod> dayPeriod;
                            @XmlAttribute(name = "type")
                            protected String type;

                            /**
                             * Gets the value of the dayPeriod property.
                             * 
                             * <p>
                             * This accessor method returns a reference to the live list,
                             * not a snapshot. Therefore any modification you make to the
                             * returned list will be present inside the JAXB object.
                             * This is why there is not a <CODE>set</CODE> method for the dayPeriod property.
                             * 
                             * <p>
                             * For example, to add a new item, do as follows:
                             * <pre>
                             *    getDayPeriod().add(newItem);
                             * </pre>
                             * 
                             * 
                             * <p>
                             * Objects of the following type(s) are allowed in the list
                             * {@link Ldml.Dates.Calendars.Calendar.DayPeriods.DayPeriodContext.DayPeriodWidth.DayPeriod }
                             * 
                             * 
                             */
                            public List<Ldml.Dates.Calendars.Calendar.DayPeriods.DayPeriodContext.DayPeriodWidth.DayPeriod> getDayPeriod() {
                                if (dayPeriod == null) {
                                    dayPeriod = new ArrayList<Ldml.Dates.Calendars.Calendar.DayPeriods.DayPeriodContext.DayPeriodWidth.DayPeriod>();
                                }
                                return this.dayPeriod;
                            }

                            /**
                             * Obtiene el valor de la propiedad type.
                             * 
                             * @return
                             *     possible object is
                             *     {@link String }
                             *     
                             */
                            public String getType() {
                                return type;
                            }

                            /**
                             * Define el valor de la propiedad type.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *     
                             */
                            public void setType(String value) {
                                this.type = value;
                            }


                            /**
                             * <p>Clase Java para anonymous complex type.
                             * 
                             * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
                             * 
                             * <pre>
                             * &lt;complexType>
                             *   &lt;simpleContent>
                             *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                             *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
                             *     &lt;/extension>
                             *   &lt;/simpleContent>
                             * &lt;/complexType>
                             * </pre>
                             * 
                             * 
                             */
                            @XmlAccessorType(XmlAccessType.FIELD)
                            @XmlType(name = "", propOrder = {
                                "value"
                            })
                            public static class DayPeriod {

                                @XmlValue
                                protected String value;
                                @XmlAttribute(name = "type")
                                protected String type;

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

                                /**
                                 * Obtiene el valor de la propiedad type.
                                 * 
                                 * @return
                                 *     possible object is
                                 *     {@link String }
                                 *     
                                 */
                                public String getType() {
                                    return type;
                                }

                                /**
                                 * Define el valor de la propiedad type.
                                 * 
                                 * @param value
                                 *     allowed object is
                                 *     {@link String }
                                 *     
                                 */
                                public void setType(String value) {
                                    this.type = value;
                                }

                            }

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
                 *         &lt;element name="dayContext" maxOccurs="unbounded" minOccurs="0">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;sequence>
                 *                   &lt;element name="dayWidth" maxOccurs="unbounded" minOccurs="0">
                 *                     &lt;complexType>
                 *                       &lt;complexContent>
                 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                           &lt;sequence>
                 *                             &lt;element name="day" maxOccurs="unbounded" minOccurs="0">
                 *                               &lt;complexType>
                 *                                 &lt;simpleContent>
                 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                 *                                     &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
                 *                                   &lt;/extension>
                 *                                 &lt;/simpleContent>
                 *                               &lt;/complexType>
                 *                             &lt;/element>
                 *                           &lt;/sequence>
                 *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
                 *                         &lt;/restriction>
                 *                       &lt;/complexContent>
                 *                     &lt;/complexType>
                 *                   &lt;/element>
                 *                 &lt;/sequence>
                 *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
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
                    "dayContext"
                })
                public static class Days {

                    protected List<Ldml.Dates.Calendars.Calendar.Days.DayContext> dayContext;

                    /**
                     * Gets the value of the dayContext property.
                     * 
                     * <p>
                     * This accessor method returns a reference to the live list,
                     * not a snapshot. Therefore any modification you make to the
                     * returned list will be present inside the JAXB object.
                     * This is why there is not a <CODE>set</CODE> method for the dayContext property.
                     * 
                     * <p>
                     * For example, to add a new item, do as follows:
                     * <pre>
                     *    getDayContext().add(newItem);
                     * </pre>
                     * 
                     * 
                     * <p>
                     * Objects of the following type(s) are allowed in the list
                     * {@link Ldml.Dates.Calendars.Calendar.Days.DayContext }
                     * 
                     * 
                     */
                    public List<Ldml.Dates.Calendars.Calendar.Days.DayContext> getDayContext() {
                        if (dayContext == null) {
                            dayContext = new ArrayList<Ldml.Dates.Calendars.Calendar.Days.DayContext>();
                        }
                        return this.dayContext;
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
                     *         &lt;element name="dayWidth" maxOccurs="unbounded" minOccurs="0">
                     *           &lt;complexType>
                     *             &lt;complexContent>
                     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *                 &lt;sequence>
                     *                   &lt;element name="day" maxOccurs="unbounded" minOccurs="0">
                     *                     &lt;complexType>
                     *                       &lt;simpleContent>
                     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                     *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
                     *                         &lt;/extension>
                     *                       &lt;/simpleContent>
                     *                     &lt;/complexType>
                     *                   &lt;/element>
                     *                 &lt;/sequence>
                     *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
                     *               &lt;/restriction>
                     *             &lt;/complexContent>
                     *           &lt;/complexType>
                     *         &lt;/element>
                     *       &lt;/sequence>
                     *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
                     *     &lt;/restriction>
                     *   &lt;/complexContent>
                     * &lt;/complexType>
                     * </pre>
                     * 
                     * 
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                        "dayWidth"
                    })
                    public static class DayContext {

                        protected List<Ldml.Dates.Calendars.Calendar.Days.DayContext.DayWidth> dayWidth;
                        @XmlAttribute(name = "type")
                        protected String type;

                        /**
                         * Gets the value of the dayWidth property.
                         * 
                         * <p>
                         * This accessor method returns a reference to the live list,
                         * not a snapshot. Therefore any modification you make to the
                         * returned list will be present inside the JAXB object.
                         * This is why there is not a <CODE>set</CODE> method for the dayWidth property.
                         * 
                         * <p>
                         * For example, to add a new item, do as follows:
                         * <pre>
                         *    getDayWidth().add(newItem);
                         * </pre>
                         * 
                         * 
                         * <p>
                         * Objects of the following type(s) are allowed in the list
                         * {@link Ldml.Dates.Calendars.Calendar.Days.DayContext.DayWidth }
                         * 
                         * 
                         */
                        public List<Ldml.Dates.Calendars.Calendar.Days.DayContext.DayWidth> getDayWidth() {
                            if (dayWidth == null) {
                                dayWidth = new ArrayList<Ldml.Dates.Calendars.Calendar.Days.DayContext.DayWidth>();
                            }
                            return this.dayWidth;
                        }

                        /**
                         * Obtiene el valor de la propiedad type.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getType() {
                            return type;
                        }

                        /**
                         * Define el valor de la propiedad type.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setType(String value) {
                            this.type = value;
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
                         *         &lt;element name="day" maxOccurs="unbounded" minOccurs="0">
                         *           &lt;complexType>
                         *             &lt;simpleContent>
                         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                         *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
                         *               &lt;/extension>
                         *             &lt;/simpleContent>
                         *           &lt;/complexType>
                         *         &lt;/element>
                         *       &lt;/sequence>
                         *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
                         *     &lt;/restriction>
                         *   &lt;/complexContent>
                         * &lt;/complexType>
                         * </pre>
                         * 
                         * 
                         */
                        @XmlAccessorType(XmlAccessType.FIELD)
                        @XmlType(name = "", propOrder = {
                            "day"
                        })
                        public static class DayWidth {

                            protected List<Ldml.Dates.Calendars.Calendar.Days.DayContext.DayWidth.Day> day;
                            @XmlAttribute(name = "type")
                            protected String type;

                            /**
                             * Gets the value of the day property.
                             * 
                             * <p>
                             * This accessor method returns a reference to the live list,
                             * not a snapshot. Therefore any modification you make to the
                             * returned list will be present inside the JAXB object.
                             * This is why there is not a <CODE>set</CODE> method for the day property.
                             * 
                             * <p>
                             * For example, to add a new item, do as follows:
                             * <pre>
                             *    getDay().add(newItem);
                             * </pre>
                             * 
                             * 
                             * <p>
                             * Objects of the following type(s) are allowed in the list
                             * {@link Ldml.Dates.Calendars.Calendar.Days.DayContext.DayWidth.Day }
                             * 
                             * 
                             */
                            public List<Ldml.Dates.Calendars.Calendar.Days.DayContext.DayWidth.Day> getDay() {
                                if (day == null) {
                                    day = new ArrayList<Ldml.Dates.Calendars.Calendar.Days.DayContext.DayWidth.Day>();
                                }
                                return this.day;
                            }

                            /**
                             * Obtiene el valor de la propiedad type.
                             * 
                             * @return
                             *     possible object is
                             *     {@link String }
                             *     
                             */
                            public String getType() {
                                return type;
                            }

                            /**
                             * Define el valor de la propiedad type.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *     
                             */
                            public void setType(String value) {
                                this.type = value;
                            }


                            /**
                             * <p>Clase Java para anonymous complex type.
                             * 
                             * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
                             * 
                             * <pre>
                             * &lt;complexType>
                             *   &lt;simpleContent>
                             *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                             *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
                             *     &lt;/extension>
                             *   &lt;/simpleContent>
                             * &lt;/complexType>
                             * </pre>
                             * 
                             * 
                             */
                            @XmlAccessorType(XmlAccessType.FIELD)
                            @XmlType(name = "", propOrder = {
                                "value"
                            })
                            public static class Day {

                                @XmlValue
                                protected String value;
                                @XmlAttribute(name = "type")
                                protected String type;

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

                                /**
                                 * Obtiene el valor de la propiedad type.
                                 * 
                                 * @return
                                 *     possible object is
                                 *     {@link String }
                                 *     
                                 */
                                public String getType() {
                                    return type;
                                }

                                /**
                                 * Define el valor de la propiedad type.
                                 * 
                                 * @param value
                                 *     allowed object is
                                 *     {@link String }
                                 *     
                                 */
                                public void setType(String value) {
                                    this.type = value;
                                }

                            }

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
                 *         &lt;element name="eraNames" minOccurs="0">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;sequence>
                 *                   &lt;element name="era" maxOccurs="unbounded" minOccurs="0">
                 *                     &lt;complexType>
                 *                       &lt;simpleContent>
                 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                 *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}byte" />
                 *                           &lt;attribute name="alt" type="{http://www.w3.org/2001/XMLSchema}string" />
                 *                         &lt;/extension>
                 *                       &lt;/simpleContent>
                 *                     &lt;/complexType>
                 *                   &lt;/element>
                 *                 &lt;/sequence>
                 *               &lt;/restriction>
                 *             &lt;/complexContent>
                 *           &lt;/complexType>
                 *         &lt;/element>
                 *         &lt;element name="eraAbbr">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;sequence>
                 *                   &lt;element name="era" maxOccurs="unbounded" minOccurs="0">
                 *                     &lt;complexType>
                 *                       &lt;simpleContent>
                 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                 *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}byte" />
                 *                           &lt;attribute name="alt" type="{http://www.w3.org/2001/XMLSchema}string" />
                 *                           &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
                 *                         &lt;/extension>
                 *                       &lt;/simpleContent>
                 *                     &lt;/complexType>
                 *                   &lt;/element>
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
                    "eraNames",
                    "eraAbbr"
                })
                public static class Eras {

                    protected Ldml.Dates.Calendars.Calendar.Eras.EraNames eraNames;
                    @XmlElement(required = true)
                    protected Ldml.Dates.Calendars.Calendar.Eras.EraAbbr eraAbbr;

                    /**
                     * Obtiene el valor de la propiedad eraNames.
                     * 
                     * @return
                     *     possible object is
                     *     {@link Ldml.Dates.Calendars.Calendar.Eras.EraNames }
                     *     
                     */
                    public Ldml.Dates.Calendars.Calendar.Eras.EraNames getEraNames() {
                        return eraNames;
                    }

                    /**
                     * Define el valor de la propiedad eraNames.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link Ldml.Dates.Calendars.Calendar.Eras.EraNames }
                     *     
                     */
                    public void setEraNames(Ldml.Dates.Calendars.Calendar.Eras.EraNames value) {
                        this.eraNames = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad eraAbbr.
                     * 
                     * @return
                     *     possible object is
                     *     {@link Ldml.Dates.Calendars.Calendar.Eras.EraAbbr }
                     *     
                     */
                    public Ldml.Dates.Calendars.Calendar.Eras.EraAbbr getEraAbbr() {
                        return eraAbbr;
                    }

                    /**
                     * Define el valor de la propiedad eraAbbr.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link Ldml.Dates.Calendars.Calendar.Eras.EraAbbr }
                     *     
                     */
                    public void setEraAbbr(Ldml.Dates.Calendars.Calendar.Eras.EraAbbr value) {
                        this.eraAbbr = value;
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
                     *         &lt;element name="era" maxOccurs="unbounded" minOccurs="0">
                     *           &lt;complexType>
                     *             &lt;simpleContent>
                     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                     *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}byte" />
                     *                 &lt;attribute name="alt" type="{http://www.w3.org/2001/XMLSchema}string" />
                     *                 &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
                     *               &lt;/extension>
                     *             &lt;/simpleContent>
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
                        "era"
                    })
                    public static class EraAbbr {

                        protected List<Ldml.Dates.Calendars.Calendar.Eras.EraAbbr.Era> era;

                        /**
                         * Gets the value of the era property.
                         * 
                         * <p>
                         * This accessor method returns a reference to the live list,
                         * not a snapshot. Therefore any modification you make to the
                         * returned list will be present inside the JAXB object.
                         * This is why there is not a <CODE>set</CODE> method for the era property.
                         * 
                         * <p>
                         * For example, to add a new item, do as follows:
                         * <pre>
                         *    getEra().add(newItem);
                         * </pre>
                         * 
                         * 
                         * <p>
                         * Objects of the following type(s) are allowed in the list
                         * {@link Ldml.Dates.Calendars.Calendar.Eras.EraAbbr.Era }
                         * 
                         * 
                         */
                        public List<Ldml.Dates.Calendars.Calendar.Eras.EraAbbr.Era> getEra() {
                            if (era == null) {
                                era = new ArrayList<Ldml.Dates.Calendars.Calendar.Eras.EraAbbr.Era>();
                            }
                            return this.era;
                        }


                        /**
                         * <p>Clase Java para anonymous complex type.
                         * 
                         * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
                         * 
                         * <pre>
                         * &lt;complexType>
                         *   &lt;simpleContent>
                         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                         *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}byte" />
                         *       &lt;attribute name="alt" type="{http://www.w3.org/2001/XMLSchema}string" />
                         *       &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
                         *     &lt;/extension>
                         *   &lt;/simpleContent>
                         * &lt;/complexType>
                         * </pre>
                         * 
                         * 
                         */
                        @XmlAccessorType(XmlAccessType.FIELD)
                        @XmlType(name = "", propOrder = {
                            "value"
                        })
                        public static class Era {

                            @XmlValue
                            protected String value;
                            @XmlAttribute(name = "type")
                            protected Byte type;
                            @XmlAttribute(name = "alt")
                            protected String alt;
                            @XmlAttribute(name = "draft")
                            protected String draft;

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

                            /**
                             * Obtiene el valor de la propiedad type.
                             * 
                             * @return
                             *     possible object is
                             *     {@link Byte }
                             *     
                             */
                            public Byte getType() {
                                return type;
                            }

                            /**
                             * Define el valor de la propiedad type.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link Byte }
                             *     
                             */
                            public void setType(Byte value) {
                                this.type = value;
                            }

                            /**
                             * Obtiene el valor de la propiedad alt.
                             * 
                             * @return
                             *     possible object is
                             *     {@link String }
                             *     
                             */
                            public String getAlt() {
                                return alt;
                            }

                            /**
                             * Define el valor de la propiedad alt.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *     
                             */
                            public void setAlt(String value) {
                                this.alt = value;
                            }

                            /**
                             * Obtiene el valor de la propiedad draft.
                             * 
                             * @return
                             *     possible object is
                             *     {@link String }
                             *     
                             */
                            public String getDraft() {
                                return draft;
                            }

                            /**
                             * Define el valor de la propiedad draft.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *     
                             */
                            public void setDraft(String value) {
                                this.draft = value;
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
                     *         &lt;element name="era" maxOccurs="unbounded" minOccurs="0">
                     *           &lt;complexType>
                     *             &lt;simpleContent>
                     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                     *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}byte" />
                     *                 &lt;attribute name="alt" type="{http://www.w3.org/2001/XMLSchema}string" />
                     *               &lt;/extension>
                     *             &lt;/simpleContent>
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
                        "era"
                    })
                    public static class EraNames {

                        protected List<Ldml.Dates.Calendars.Calendar.Eras.EraNames.Era> era;

                        /**
                         * Gets the value of the era property.
                         * 
                         * <p>
                         * This accessor method returns a reference to the live list,
                         * not a snapshot. Therefore any modification you make to the
                         * returned list will be present inside the JAXB object.
                         * This is why there is not a <CODE>set</CODE> method for the era property.
                         * 
                         * <p>
                         * For example, to add a new item, do as follows:
                         * <pre>
                         *    getEra().add(newItem);
                         * </pre>
                         * 
                         * 
                         * <p>
                         * Objects of the following type(s) are allowed in the list
                         * {@link Ldml.Dates.Calendars.Calendar.Eras.EraNames.Era }
                         * 
                         * 
                         */
                        public List<Ldml.Dates.Calendars.Calendar.Eras.EraNames.Era> getEra() {
                            if (era == null) {
                                era = new ArrayList<Ldml.Dates.Calendars.Calendar.Eras.EraNames.Era>();
                            }
                            return this.era;
                        }


                        /**
                         * <p>Clase Java para anonymous complex type.
                         * 
                         * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
                         * 
                         * <pre>
                         * &lt;complexType>
                         *   &lt;simpleContent>
                         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                         *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}byte" />
                         *       &lt;attribute name="alt" type="{http://www.w3.org/2001/XMLSchema}string" />
                         *     &lt;/extension>
                         *   &lt;/simpleContent>
                         * &lt;/complexType>
                         * </pre>
                         * 
                         * 
                         */
                        @XmlAccessorType(XmlAccessType.FIELD)
                        @XmlType(name = "", propOrder = {
                            "value"
                        })
                        public static class Era {

                            @XmlValue
                            protected String value;
                            @XmlAttribute(name = "type")
                            protected Byte type;
                            @XmlAttribute(name = "alt")
                            protected String alt;

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

                            /**
                             * Obtiene el valor de la propiedad type.
                             * 
                             * @return
                             *     possible object is
                             *     {@link Byte }
                             *     
                             */
                            public Byte getType() {
                                return type;
                            }

                            /**
                             * Define el valor de la propiedad type.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link Byte }
                             *     
                             */
                            public void setType(Byte value) {
                                this.type = value;
                            }

                            /**
                             * Obtiene el valor de la propiedad alt.
                             * 
                             * @return
                             *     possible object is
                             *     {@link String }
                             *     
                             */
                            public String getAlt() {
                                return alt;
                            }

                            /**
                             * Define el valor de la propiedad alt.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *     
                             */
                            public void setAlt(String value) {
                                this.alt = value;
                            }

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
                 *         &lt;element name="monthContext" maxOccurs="unbounded" minOccurs="0">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;sequence>
                 *                   &lt;element name="monthWidth" maxOccurs="unbounded" minOccurs="0">
                 *                     &lt;complexType>
                 *                       &lt;complexContent>
                 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                           &lt;sequence>
                 *                             &lt;element name="month" maxOccurs="unbounded" minOccurs="0">
                 *                               &lt;complexType>
                 *                                 &lt;simpleContent>
                 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                 *                                     &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}byte" />
                 *                                   &lt;/extension>
                 *                                 &lt;/simpleContent>
                 *                               &lt;/complexType>
                 *                             &lt;/element>
                 *                           &lt;/sequence>
                 *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
                 *                         &lt;/restriction>
                 *                       &lt;/complexContent>
                 *                     &lt;/complexType>
                 *                   &lt;/element>
                 *                 &lt;/sequence>
                 *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
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
                    "monthContext"
                })
                public static class Months {

                    protected List<Ldml.Dates.Calendars.Calendar.Months.MonthContext> monthContext;

                    /**
                     * Gets the value of the monthContext property.
                     * 
                     * <p>
                     * This accessor method returns a reference to the live list,
                     * not a snapshot. Therefore any modification you make to the
                     * returned list will be present inside the JAXB object.
                     * This is why there is not a <CODE>set</CODE> method for the monthContext property.
                     * 
                     * <p>
                     * For example, to add a new item, do as follows:
                     * <pre>
                     *    getMonthContext().add(newItem);
                     * </pre>
                     * 
                     * 
                     * <p>
                     * Objects of the following type(s) are allowed in the list
                     * {@link Ldml.Dates.Calendars.Calendar.Months.MonthContext }
                     * 
                     * 
                     */
                    public List<Ldml.Dates.Calendars.Calendar.Months.MonthContext> getMonthContext() {
                        if (monthContext == null) {
                            monthContext = new ArrayList<Ldml.Dates.Calendars.Calendar.Months.MonthContext>();
                        }
                        return this.monthContext;
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
                     *         &lt;element name="monthWidth" maxOccurs="unbounded" minOccurs="0">
                     *           &lt;complexType>
                     *             &lt;complexContent>
                     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *                 &lt;sequence>
                     *                   &lt;element name="month" maxOccurs="unbounded" minOccurs="0">
                     *                     &lt;complexType>
                     *                       &lt;simpleContent>
                     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                     *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}byte" />
                     *                         &lt;/extension>
                     *                       &lt;/simpleContent>
                     *                     &lt;/complexType>
                     *                   &lt;/element>
                     *                 &lt;/sequence>
                     *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
                     *               &lt;/restriction>
                     *             &lt;/complexContent>
                     *           &lt;/complexType>
                     *         &lt;/element>
                     *       &lt;/sequence>
                     *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
                     *     &lt;/restriction>
                     *   &lt;/complexContent>
                     * &lt;/complexType>
                     * </pre>
                     * 
                     * 
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                        "monthWidth"
                    })
                    public static class MonthContext {

                        protected List<Ldml.Dates.Calendars.Calendar.Months.MonthContext.MonthWidth> monthWidth;
                        @XmlAttribute(name = "type")
                        protected String type;

                        /**
                         * Gets the value of the monthWidth property.
                         * 
                         * <p>
                         * This accessor method returns a reference to the live list,
                         * not a snapshot. Therefore any modification you make to the
                         * returned list will be present inside the JAXB object.
                         * This is why there is not a <CODE>set</CODE> method for the monthWidth property.
                         * 
                         * <p>
                         * For example, to add a new item, do as follows:
                         * <pre>
                         *    getMonthWidth().add(newItem);
                         * </pre>
                         * 
                         * 
                         * <p>
                         * Objects of the following type(s) are allowed in the list
                         * {@link Ldml.Dates.Calendars.Calendar.Months.MonthContext.MonthWidth }
                         * 
                         * 
                         */
                        public List<Ldml.Dates.Calendars.Calendar.Months.MonthContext.MonthWidth> getMonthWidth() {
                            if (monthWidth == null) {
                                monthWidth = new ArrayList<Ldml.Dates.Calendars.Calendar.Months.MonthContext.MonthWidth>();
                            }
                            return this.monthWidth;
                        }

                        /**
                         * Obtiene el valor de la propiedad type.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getType() {
                            return type;
                        }

                        /**
                         * Define el valor de la propiedad type.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setType(String value) {
                            this.type = value;
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
                         *         &lt;element name="month" maxOccurs="unbounded" minOccurs="0">
                         *           &lt;complexType>
                         *             &lt;simpleContent>
                         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                         *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}byte" />
                         *               &lt;/extension>
                         *             &lt;/simpleContent>
                         *           &lt;/complexType>
                         *         &lt;/element>
                         *       &lt;/sequence>
                         *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
                         *     &lt;/restriction>
                         *   &lt;/complexContent>
                         * &lt;/complexType>
                         * </pre>
                         * 
                         * 
                         */
                        @XmlAccessorType(XmlAccessType.FIELD)
                        @XmlType(name = "", propOrder = {
                            "month"
                        })
                        public static class MonthWidth {

                            protected List<Ldml.Dates.Calendars.Calendar.Months.MonthContext.MonthWidth.Month> month;
                            @XmlAttribute(name = "type")
                            protected String type;

                            /**
                             * Gets the value of the month property.
                             * 
                             * <p>
                             * This accessor method returns a reference to the live list,
                             * not a snapshot. Therefore any modification you make to the
                             * returned list will be present inside the JAXB object.
                             * This is why there is not a <CODE>set</CODE> method for the month property.
                             * 
                             * <p>
                             * For example, to add a new item, do as follows:
                             * <pre>
                             *    getMonth().add(newItem);
                             * </pre>
                             * 
                             * 
                             * <p>
                             * Objects of the following type(s) are allowed in the list
                             * {@link Ldml.Dates.Calendars.Calendar.Months.MonthContext.MonthWidth.Month }
                             * 
                             * 
                             */
                            public List<Ldml.Dates.Calendars.Calendar.Months.MonthContext.MonthWidth.Month> getMonth() {
                                if (month == null) {
                                    month = new ArrayList<Ldml.Dates.Calendars.Calendar.Months.MonthContext.MonthWidth.Month>();
                                }
                                return this.month;
                            }

                            /**
                             * Obtiene el valor de la propiedad type.
                             * 
                             * @return
                             *     possible object is
                             *     {@link String }
                             *     
                             */
                            public String getType() {
                                return type;
                            }

                            /**
                             * Define el valor de la propiedad type.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *     
                             */
                            public void setType(String value) {
                                this.type = value;
                            }


                            /**
                             * <p>Clase Java para anonymous complex type.
                             * 
                             * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
                             * 
                             * <pre>
                             * &lt;complexType>
                             *   &lt;simpleContent>
                             *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                             *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}byte" />
                             *     &lt;/extension>
                             *   &lt;/simpleContent>
                             * &lt;/complexType>
                             * </pre>
                             * 
                             * 
                             */
                            @XmlAccessorType(XmlAccessType.FIELD)
                            @XmlType(name = "", propOrder = {
                                "value"
                            })
                            public static class Month {

                                @XmlValue
                                protected String value;
                                @XmlAttribute(name = "type")
                                protected Byte type;

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

                                /**
                                 * Obtiene el valor de la propiedad type.
                                 * 
                                 * @return
                                 *     possible object is
                                 *     {@link Byte }
                                 *     
                                 */
                                public Byte getType() {
                                    return type;
                                }

                                /**
                                 * Define el valor de la propiedad type.
                                 * 
                                 * @param value
                                 *     allowed object is
                                 *     {@link Byte }
                                 *     
                                 */
                                public void setType(Byte value) {
                                    this.type = value;
                                }

                            }

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
                 *         &lt;element name="quarterContext" maxOccurs="unbounded" minOccurs="0">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;sequence>
                 *                   &lt;element name="quarterWidth" maxOccurs="unbounded" minOccurs="0">
                 *                     &lt;complexType>
                 *                       &lt;complexContent>
                 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                           &lt;sequence>
                 *                             &lt;element name="quarter" maxOccurs="unbounded" minOccurs="0">
                 *                               &lt;complexType>
                 *                                 &lt;simpleContent>
                 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                 *                                     &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}byte" />
                 *                                   &lt;/extension>
                 *                                 &lt;/simpleContent>
                 *                               &lt;/complexType>
                 *                             &lt;/element>
                 *                           &lt;/sequence>
                 *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
                 *                         &lt;/restriction>
                 *                       &lt;/complexContent>
                 *                     &lt;/complexType>
                 *                   &lt;/element>
                 *                 &lt;/sequence>
                 *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
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
                    "quarterContext"
                })
                public static class Quarters {

                    protected List<Ldml.Dates.Calendars.Calendar.Quarters.QuarterContext> quarterContext;

                    /**
                     * Gets the value of the quarterContext property.
                     * 
                     * <p>
                     * This accessor method returns a reference to the live list,
                     * not a snapshot. Therefore any modification you make to the
                     * returned list will be present inside the JAXB object.
                     * This is why there is not a <CODE>set</CODE> method for the quarterContext property.
                     * 
                     * <p>
                     * For example, to add a new item, do as follows:
                     * <pre>
                     *    getQuarterContext().add(newItem);
                     * </pre>
                     * 
                     * 
                     * <p>
                     * Objects of the following type(s) are allowed in the list
                     * {@link Ldml.Dates.Calendars.Calendar.Quarters.QuarterContext }
                     * 
                     * 
                     */
                    public List<Ldml.Dates.Calendars.Calendar.Quarters.QuarterContext> getQuarterContext() {
                        if (quarterContext == null) {
                            quarterContext = new ArrayList<Ldml.Dates.Calendars.Calendar.Quarters.QuarterContext>();
                        }
                        return this.quarterContext;
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
                     *         &lt;element name="quarterWidth" maxOccurs="unbounded" minOccurs="0">
                     *           &lt;complexType>
                     *             &lt;complexContent>
                     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *                 &lt;sequence>
                     *                   &lt;element name="quarter" maxOccurs="unbounded" minOccurs="0">
                     *                     &lt;complexType>
                     *                       &lt;simpleContent>
                     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                     *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}byte" />
                     *                         &lt;/extension>
                     *                       &lt;/simpleContent>
                     *                     &lt;/complexType>
                     *                   &lt;/element>
                     *                 &lt;/sequence>
                     *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
                     *               &lt;/restriction>
                     *             &lt;/complexContent>
                     *           &lt;/complexType>
                     *         &lt;/element>
                     *       &lt;/sequence>
                     *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
                     *     &lt;/restriction>
                     *   &lt;/complexContent>
                     * &lt;/complexType>
                     * </pre>
                     * 
                     * 
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                        "quarterWidth"
                    })
                    public static class QuarterContext {

                        protected List<Ldml.Dates.Calendars.Calendar.Quarters.QuarterContext.QuarterWidth> quarterWidth;
                        @XmlAttribute(name = "type")
                        protected String type;

                        /**
                         * Gets the value of the quarterWidth property.
                         * 
                         * <p>
                         * This accessor method returns a reference to the live list,
                         * not a snapshot. Therefore any modification you make to the
                         * returned list will be present inside the JAXB object.
                         * This is why there is not a <CODE>set</CODE> method for the quarterWidth property.
                         * 
                         * <p>
                         * For example, to add a new item, do as follows:
                         * <pre>
                         *    getQuarterWidth().add(newItem);
                         * </pre>
                         * 
                         * 
                         * <p>
                         * Objects of the following type(s) are allowed in the list
                         * {@link Ldml.Dates.Calendars.Calendar.Quarters.QuarterContext.QuarterWidth }
                         * 
                         * 
                         */
                        public List<Ldml.Dates.Calendars.Calendar.Quarters.QuarterContext.QuarterWidth> getQuarterWidth() {
                            if (quarterWidth == null) {
                                quarterWidth = new ArrayList<Ldml.Dates.Calendars.Calendar.Quarters.QuarterContext.QuarterWidth>();
                            }
                            return this.quarterWidth;
                        }

                        /**
                         * Obtiene el valor de la propiedad type.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getType() {
                            return type;
                        }

                        /**
                         * Define el valor de la propiedad type.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setType(String value) {
                            this.type = value;
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
                         *         &lt;element name="quarter" maxOccurs="unbounded" minOccurs="0">
                         *           &lt;complexType>
                         *             &lt;simpleContent>
                         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                         *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}byte" />
                         *               &lt;/extension>
                         *             &lt;/simpleContent>
                         *           &lt;/complexType>
                         *         &lt;/element>
                         *       &lt;/sequence>
                         *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
                         *     &lt;/restriction>
                         *   &lt;/complexContent>
                         * &lt;/complexType>
                         * </pre>
                         * 
                         * 
                         */
                        @XmlAccessorType(XmlAccessType.FIELD)
                        @XmlType(name = "", propOrder = {
                            "quarter"
                        })
                        public static class QuarterWidth {

                            protected List<Ldml.Dates.Calendars.Calendar.Quarters.QuarterContext.QuarterWidth.Quarter> quarter;
                            @XmlAttribute(name = "type")
                            protected String type;

                            /**
                             * Gets the value of the quarter property.
                             * 
                             * <p>
                             * This accessor method returns a reference to the live list,
                             * not a snapshot. Therefore any modification you make to the
                             * returned list will be present inside the JAXB object.
                             * This is why there is not a <CODE>set</CODE> method for the quarter property.
                             * 
                             * <p>
                             * For example, to add a new item, do as follows:
                             * <pre>
                             *    getQuarter().add(newItem);
                             * </pre>
                             * 
                             * 
                             * <p>
                             * Objects of the following type(s) are allowed in the list
                             * {@link Ldml.Dates.Calendars.Calendar.Quarters.QuarterContext.QuarterWidth.Quarter }
                             * 
                             * 
                             */
                            public List<Ldml.Dates.Calendars.Calendar.Quarters.QuarterContext.QuarterWidth.Quarter> getQuarter() {
                                if (quarter == null) {
                                    quarter = new ArrayList<Ldml.Dates.Calendars.Calendar.Quarters.QuarterContext.QuarterWidth.Quarter>();
                                }
                                return this.quarter;
                            }

                            /**
                             * Obtiene el valor de la propiedad type.
                             * 
                             * @return
                             *     possible object is
                             *     {@link String }
                             *     
                             */
                            public String getType() {
                                return type;
                            }

                            /**
                             * Define el valor de la propiedad type.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *     
                             */
                            public void setType(String value) {
                                this.type = value;
                            }


                            /**
                             * <p>Clase Java para anonymous complex type.
                             * 
                             * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
                             * 
                             * <pre>
                             * &lt;complexType>
                             *   &lt;simpleContent>
                             *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                             *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}byte" />
                             *     &lt;/extension>
                             *   &lt;/simpleContent>
                             * &lt;/complexType>
                             * </pre>
                             * 
                             * 
                             */
                            @XmlAccessorType(XmlAccessType.FIELD)
                            @XmlType(name = "", propOrder = {
                                "value"
                            })
                            public static class Quarter {

                                @XmlValue
                                protected String value;
                                @XmlAttribute(name = "type")
                                protected Byte type;

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

                                /**
                                 * Obtiene el valor de la propiedad type.
                                 * 
                                 * @return
                                 *     possible object is
                                 *     {@link Byte }
                                 *     
                                 */
                                public Byte getType() {
                                    return type;
                                }

                                /**
                                 * Define el valor de la propiedad type.
                                 * 
                                 * @param value
                                 *     allowed object is
                                 *     {@link Byte }
                                 *     
                                 */
                                public void setType(Byte value) {
                                    this.type = value;
                                }

                            }

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
                 *         &lt;element name="timeFormatLength" maxOccurs="unbounded" minOccurs="0">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;sequence>
                 *                   &lt;element name="timeFormat">
                 *                     &lt;complexType>
                 *                       &lt;complexContent>
                 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                           &lt;sequence>
                 *                             &lt;element name="pattern" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *                           &lt;/sequence>
                 *                         &lt;/restriction>
                 *                       &lt;/complexContent>
                 *                     &lt;/complexType>
                 *                   &lt;/element>
                 *                 &lt;/sequence>
                 *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
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
                    "timeFormatLength"
                })
                public static class TimeFormats {

                    protected List<Ldml.Dates.Calendars.Calendar.TimeFormats.TimeFormatLength> timeFormatLength;

                    /**
                     * Gets the value of the timeFormatLength property.
                     * 
                     * <p>
                     * This accessor method returns a reference to the live list,
                     * not a snapshot. Therefore any modification you make to the
                     * returned list will be present inside the JAXB object.
                     * This is why there is not a <CODE>set</CODE> method for the timeFormatLength property.
                     * 
                     * <p>
                     * For example, to add a new item, do as follows:
                     * <pre>
                     *    getTimeFormatLength().add(newItem);
                     * </pre>
                     * 
                     * 
                     * <p>
                     * Objects of the following type(s) are allowed in the list
                     * {@link Ldml.Dates.Calendars.Calendar.TimeFormats.TimeFormatLength }
                     * 
                     * 
                     */
                    public List<Ldml.Dates.Calendars.Calendar.TimeFormats.TimeFormatLength> getTimeFormatLength() {
                        if (timeFormatLength == null) {
                            timeFormatLength = new ArrayList<Ldml.Dates.Calendars.Calendar.TimeFormats.TimeFormatLength>();
                        }
                        return this.timeFormatLength;
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
                     *         &lt;element name="timeFormat">
                     *           &lt;complexType>
                     *             &lt;complexContent>
                     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *                 &lt;sequence>
                     *                   &lt;element name="pattern" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *                 &lt;/sequence>
                     *               &lt;/restriction>
                     *             &lt;/complexContent>
                     *           &lt;/complexType>
                     *         &lt;/element>
                     *       &lt;/sequence>
                     *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
                     *     &lt;/restriction>
                     *   &lt;/complexContent>
                     * &lt;/complexType>
                     * </pre>
                     * 
                     * 
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                        "timeFormat"
                    })
                    public static class TimeFormatLength {

                        @XmlElement(required = true)
                        protected Ldml.Dates.Calendars.Calendar.TimeFormats.TimeFormatLength.TimeFormat timeFormat;
                        @XmlAttribute(name = "type")
                        protected String type;

                        /**
                         * Obtiene el valor de la propiedad timeFormat.
                         * 
                         * @return
                         *     possible object is
                         *     {@link Ldml.Dates.Calendars.Calendar.TimeFormats.TimeFormatLength.TimeFormat }
                         *     
                         */
                        public Ldml.Dates.Calendars.Calendar.TimeFormats.TimeFormatLength.TimeFormat getTimeFormat() {
                            return timeFormat;
                        }

                        /**
                         * Define el valor de la propiedad timeFormat.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link Ldml.Dates.Calendars.Calendar.TimeFormats.TimeFormatLength.TimeFormat }
                         *     
                         */
                        public void setTimeFormat(Ldml.Dates.Calendars.Calendar.TimeFormats.TimeFormatLength.TimeFormat value) {
                            this.timeFormat = value;
                        }

                        /**
                         * Obtiene el valor de la propiedad type.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getType() {
                            return type;
                        }

                        /**
                         * Define el valor de la propiedad type.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setType(String value) {
                            this.type = value;
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
                         *         &lt;element name="pattern" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
                            "pattern"
                        })
                        public static class TimeFormat {

                            @XmlElement(required = true)
                            protected String pattern;

                            /**
                             * Obtiene el valor de la propiedad pattern.
                             * 
                             * @return
                             *     possible object is
                             *     {@link String }
                             *     
                             */
                            public String getPattern() {
                                return pattern;
                            }

                            /**
                             * Define el valor de la propiedad pattern.
                             * 
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *     
                             */
                            public void setPattern(String value) {
                                this.pattern = value;
                            }

                        }

                    }

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
         *         &lt;element name="field" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="displayName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="relative" maxOccurs="unbounded" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;simpleContent>
         *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}byte" />
         *                         &lt;/extension>
         *                       &lt;/simpleContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                   &lt;element name="relativeTime" maxOccurs="unbounded" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="relativeTimePattern" maxOccurs="unbounded" minOccurs="0">
         *                               &lt;complexType>
         *                                 &lt;simpleContent>
         *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                                     &lt;attribute name="count" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                                   &lt;/extension>
         *                                 &lt;/simpleContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                           &lt;/sequence>
         *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
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
            "field"
        })
        public static class Fields {

            protected List<Ldml.Dates.Fields.Field> field;

            /**
             * Gets the value of the field property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the field property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getField().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Ldml.Dates.Fields.Field }
             * 
             * 
             */
            public List<Ldml.Dates.Fields.Field> getField() {
                if (field == null) {
                    field = new ArrayList<Ldml.Dates.Fields.Field>();
                }
                return this.field;
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
             *         &lt;element name="displayName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="relative" maxOccurs="unbounded" minOccurs="0">
             *           &lt;complexType>
             *             &lt;simpleContent>
             *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}byte" />
             *               &lt;/extension>
             *             &lt;/simpleContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="relativeTime" maxOccurs="unbounded" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="relativeTimePattern" maxOccurs="unbounded" minOccurs="0">
             *                     &lt;complexType>
             *                       &lt;simpleContent>
             *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *                           &lt;attribute name="count" type="{http://www.w3.org/2001/XMLSchema}string" />
             *                         &lt;/extension>
             *                       &lt;/simpleContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                 &lt;/sequence>
             *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *       &lt;/sequence>
             *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "displayName",
                "relative",
                "relativeTime"
            })
            public static class Field {

                protected String displayName;
                protected List<Ldml.Dates.Fields.Field.Relative> relative;
                protected List<Ldml.Dates.Fields.Field.RelativeTime> relativeTime;
                @XmlAttribute(name = "type")
                protected String type;

                /**
                 * Obtiene el valor de la propiedad displayName.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getDisplayName() {
                    return displayName;
                }

                /**
                 * Define el valor de la propiedad displayName.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setDisplayName(String value) {
                    this.displayName = value;
                }

                /**
                 * Gets the value of the relative property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the relative property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getRelative().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link Ldml.Dates.Fields.Field.Relative }
                 * 
                 * 
                 */
                public List<Ldml.Dates.Fields.Field.Relative> getRelative() {
                    if (relative == null) {
                        relative = new ArrayList<Ldml.Dates.Fields.Field.Relative>();
                    }
                    return this.relative;
                }

                /**
                 * Gets the value of the relativeTime property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the relativeTime property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getRelativeTime().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link Ldml.Dates.Fields.Field.RelativeTime }
                 * 
                 * 
                 */
                public List<Ldml.Dates.Fields.Field.RelativeTime> getRelativeTime() {
                    if (relativeTime == null) {
                        relativeTime = new ArrayList<Ldml.Dates.Fields.Field.RelativeTime>();
                    }
                    return this.relativeTime;
                }

                /**
                 * Obtiene el valor de la propiedad type.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getType() {
                    return type;
                }

                /**
                 * Define el valor de la propiedad type.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setType(String value) {
                    this.type = value;
                }


                /**
                 * <p>Clase Java para anonymous complex type.
                 * 
                 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;simpleContent>
                 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                 *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}byte" />
                 *     &lt;/extension>
                 *   &lt;/simpleContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "value"
                })
                public static class Relative {

                    @XmlValue
                    protected String value;
                    @XmlAttribute(name = "type")
                    protected Byte type;

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

                    /**
                     * Obtiene el valor de la propiedad type.
                     * 
                     * @return
                     *     possible object is
                     *     {@link Byte }
                     *     
                     */
                    public Byte getType() {
                        return type;
                    }

                    /**
                     * Define el valor de la propiedad type.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link Byte }
                     *     
                     */
                    public void setType(Byte value) {
                        this.type = value;
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
                 *         &lt;element name="relativeTimePattern" maxOccurs="unbounded" minOccurs="0">
                 *           &lt;complexType>
                 *             &lt;simpleContent>
                 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                 *                 &lt;attribute name="count" type="{http://www.w3.org/2001/XMLSchema}string" />
                 *               &lt;/extension>
                 *             &lt;/simpleContent>
                 *           &lt;/complexType>
                 *         &lt;/element>
                 *       &lt;/sequence>
                 *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "relativeTimePattern"
                })
                public static class RelativeTime {

                    protected List<Ldml.Dates.Fields.Field.RelativeTime.RelativeTimePattern> relativeTimePattern;
                    @XmlAttribute(name = "type")
                    protected String type;

                    /**
                     * Gets the value of the relativeTimePattern property.
                     * 
                     * <p>
                     * This accessor method returns a reference to the live list,
                     * not a snapshot. Therefore any modification you make to the
                     * returned list will be present inside the JAXB object.
                     * This is why there is not a <CODE>set</CODE> method for the relativeTimePattern property.
                     * 
                     * <p>
                     * For example, to add a new item, do as follows:
                     * <pre>
                     *    getRelativeTimePattern().add(newItem);
                     * </pre>
                     * 
                     * 
                     * <p>
                     * Objects of the following type(s) are allowed in the list
                     * {@link Ldml.Dates.Fields.Field.RelativeTime.RelativeTimePattern }
                     * 
                     * 
                     */
                    public List<Ldml.Dates.Fields.Field.RelativeTime.RelativeTimePattern> getRelativeTimePattern() {
                        if (relativeTimePattern == null) {
                            relativeTimePattern = new ArrayList<Ldml.Dates.Fields.Field.RelativeTime.RelativeTimePattern>();
                        }
                        return this.relativeTimePattern;
                    }

                    /**
                     * Obtiene el valor de la propiedad type.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getType() {
                        return type;
                    }

                    /**
                     * Define el valor de la propiedad type.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setType(String value) {
                        this.type = value;
                    }


                    /**
                     * <p>Clase Java para anonymous complex type.
                     * 
                     * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
                     * 
                     * <pre>
                     * &lt;complexType>
                     *   &lt;simpleContent>
                     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                     *       &lt;attribute name="count" type="{http://www.w3.org/2001/XMLSchema}string" />
                     *     &lt;/extension>
                     *   &lt;/simpleContent>
                     * &lt;/complexType>
                     * </pre>
                     * 
                     * 
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                        "value"
                    })
                    public static class RelativeTimePattern {

                        @XmlValue
                        protected String value;
                        @XmlAttribute(name = "count")
                        protected String count;

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

                        /**
                         * Obtiene el valor de la propiedad count.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getCount() {
                            return count;
                        }

                        /**
                         * Define el valor de la propiedad count.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setCount(String value) {
                            this.count = value;
                        }

                    }

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
         *         &lt;element name="hourFormat" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="gmtFormat" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="gmtZeroFormat" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="regionFormat" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;simpleContent>
         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
         *               &lt;/extension>
         *             &lt;/simpleContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="fallbackFormat" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="zone" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="long" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="daylight" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                   &lt;element name="exemplarCity" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                 &lt;/sequence>
         *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="metazone" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="long">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="generic" minOccurs="0">
         *                               &lt;complexType>
         *                                 &lt;simpleContent>
         *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                                     &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                                   &lt;/extension>
         *                                 &lt;/simpleContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                             &lt;element name="standard">
         *                               &lt;complexType>
         *                                 &lt;simpleContent>
         *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                                     &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                                   &lt;/extension>
         *                                 &lt;/simpleContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                             &lt;element name="daylight" minOccurs="0">
         *                               &lt;complexType>
         *                                 &lt;simpleContent>
         *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                                     &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                                   &lt;/extension>
         *                                 &lt;/simpleContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                   &lt;element name="short" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="generic" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                             &lt;element name="standard" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                             &lt;element name="daylight" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
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
            "hourFormat",
            "gmtFormat",
            "gmtZeroFormat",
            "regionFormat",
            "fallbackFormat",
            "zone",
            "metazone"
        })
        public static class TimeZoneNames {

            @XmlElement(required = true)
            protected String hourFormat;
            @XmlElement(required = true)
            protected String gmtFormat;
            @XmlElement(required = true)
            protected String gmtZeroFormat;
            protected List<Ldml.Dates.TimeZoneNames.RegionFormat> regionFormat;
            @XmlElement(required = true)
            protected String fallbackFormat;
            protected List<Ldml.Dates.TimeZoneNames.Zone> zone;
            protected List<Ldml.Dates.TimeZoneNames.Metazone> metazone;

            /**
             * Obtiene el valor de la propiedad hourFormat.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHourFormat() {
                return hourFormat;
            }

            /**
             * Define el valor de la propiedad hourFormat.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHourFormat(String value) {
                this.hourFormat = value;
            }

            /**
             * Obtiene el valor de la propiedad gmtFormat.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getGmtFormat() {
                return gmtFormat;
            }

            /**
             * Define el valor de la propiedad gmtFormat.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setGmtFormat(String value) {
                this.gmtFormat = value;
            }

            /**
             * Obtiene el valor de la propiedad gmtZeroFormat.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getGmtZeroFormat() {
                return gmtZeroFormat;
            }

            /**
             * Define el valor de la propiedad gmtZeroFormat.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setGmtZeroFormat(String value) {
                this.gmtZeroFormat = value;
            }

            /**
             * Gets the value of the regionFormat property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the regionFormat property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getRegionFormat().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Ldml.Dates.TimeZoneNames.RegionFormat }
             * 
             * 
             */
            public List<Ldml.Dates.TimeZoneNames.RegionFormat> getRegionFormat() {
                if (regionFormat == null) {
                    regionFormat = new ArrayList<Ldml.Dates.TimeZoneNames.RegionFormat>();
                }
                return this.regionFormat;
            }

            /**
             * Obtiene el valor de la propiedad fallbackFormat.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getFallbackFormat() {
                return fallbackFormat;
            }

            /**
             * Define el valor de la propiedad fallbackFormat.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setFallbackFormat(String value) {
                this.fallbackFormat = value;
            }

            /**
             * Gets the value of the zone property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the zone property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getZone().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Ldml.Dates.TimeZoneNames.Zone }
             * 
             * 
             */
            public List<Ldml.Dates.TimeZoneNames.Zone> getZone() {
                if (zone == null) {
                    zone = new ArrayList<Ldml.Dates.TimeZoneNames.Zone>();
                }
                return this.zone;
            }

            /**
             * Gets the value of the metazone property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the metazone property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getMetazone().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Ldml.Dates.TimeZoneNames.Metazone }
             * 
             * 
             */
            public List<Ldml.Dates.TimeZoneNames.Metazone> getMetazone() {
                if (metazone == null) {
                    metazone = new ArrayList<Ldml.Dates.TimeZoneNames.Metazone>();
                }
                return this.metazone;
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
             *         &lt;element name="long">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="generic" minOccurs="0">
             *                     &lt;complexType>
             *                       &lt;simpleContent>
             *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *                           &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
             *                         &lt;/extension>
             *                       &lt;/simpleContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                   &lt;element name="standard">
             *                     &lt;complexType>
             *                       &lt;simpleContent>
             *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *                           &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
             *                         &lt;/extension>
             *                       &lt;/simpleContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                   &lt;element name="daylight" minOccurs="0">
             *                     &lt;complexType>
             *                       &lt;simpleContent>
             *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *                           &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
             *                         &lt;/extension>
             *                       &lt;/simpleContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="short" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="generic" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                   &lt;element name="standard" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                   &lt;element name="daylight" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *       &lt;/sequence>
             *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "_long",
                "_short"
            })
            public static class Metazone {

                @XmlElement(name = "long", required = true)
                protected Ldml.Dates.TimeZoneNames.Metazone.Long _long;
                @XmlElement(name = "short")
                protected Ldml.Dates.TimeZoneNames.Metazone.Short _short;
                @XmlAttribute(name = "type")
                protected String type;

                /**
                 * Obtiene el valor de la propiedad long.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Ldml.Dates.TimeZoneNames.Metazone.Long }
                 *     
                 */
                public Ldml.Dates.TimeZoneNames.Metazone.Long getLong() {
                    return _long;
                }

                /**
                 * Define el valor de la propiedad long.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Ldml.Dates.TimeZoneNames.Metazone.Long }
                 *     
                 */
                public void setLong(Ldml.Dates.TimeZoneNames.Metazone.Long value) {
                    this._long = value;
                }

                /**
                 * Obtiene el valor de la propiedad short.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Ldml.Dates.TimeZoneNames.Metazone.Short }
                 *     
                 */
                public Ldml.Dates.TimeZoneNames.Metazone.Short getShort() {
                    return _short;
                }

                /**
                 * Define el valor de la propiedad short.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Ldml.Dates.TimeZoneNames.Metazone.Short }
                 *     
                 */
                public void setShort(Ldml.Dates.TimeZoneNames.Metazone.Short value) {
                    this._short = value;
                }

                /**
                 * Obtiene el valor de la propiedad type.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getType() {
                    return type;
                }

                /**
                 * Define el valor de la propiedad type.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setType(String value) {
                    this.type = value;
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
                 *         &lt;element name="generic" minOccurs="0">
                 *           &lt;complexType>
                 *             &lt;simpleContent>
                 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                 *                 &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
                 *               &lt;/extension>
                 *             &lt;/simpleContent>
                 *           &lt;/complexType>
                 *         &lt;/element>
                 *         &lt;element name="standard">
                 *           &lt;complexType>
                 *             &lt;simpleContent>
                 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                 *                 &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
                 *               &lt;/extension>
                 *             &lt;/simpleContent>
                 *           &lt;/complexType>
                 *         &lt;/element>
                 *         &lt;element name="daylight" minOccurs="0">
                 *           &lt;complexType>
                 *             &lt;simpleContent>
                 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                 *                 &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
                 *               &lt;/extension>
                 *             &lt;/simpleContent>
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
                    "generic",
                    "standard",
                    "daylight"
                })
                public static class Long {

                    protected Ldml.Dates.TimeZoneNames.Metazone.Long.Generic generic;
                    @XmlElement(required = true)
                    protected Ldml.Dates.TimeZoneNames.Metazone.Long.Standard standard;
                    protected Ldml.Dates.TimeZoneNames.Metazone.Long.Daylight daylight;

                    /**
                     * Obtiene el valor de la propiedad generic.
                     * 
                     * @return
                     *     possible object is
                     *     {@link Ldml.Dates.TimeZoneNames.Metazone.Long.Generic }
                     *     
                     */
                    public Ldml.Dates.TimeZoneNames.Metazone.Long.Generic getGeneric() {
                        return generic;
                    }

                    /**
                     * Define el valor de la propiedad generic.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link Ldml.Dates.TimeZoneNames.Metazone.Long.Generic }
                     *     
                     */
                    public void setGeneric(Ldml.Dates.TimeZoneNames.Metazone.Long.Generic value) {
                        this.generic = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad standard.
                     * 
                     * @return
                     *     possible object is
                     *     {@link Ldml.Dates.TimeZoneNames.Metazone.Long.Standard }
                     *     
                     */
                    public Ldml.Dates.TimeZoneNames.Metazone.Long.Standard getStandard() {
                        return standard;
                    }

                    /**
                     * Define el valor de la propiedad standard.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link Ldml.Dates.TimeZoneNames.Metazone.Long.Standard }
                     *     
                     */
                    public void setStandard(Ldml.Dates.TimeZoneNames.Metazone.Long.Standard value) {
                        this.standard = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad daylight.
                     * 
                     * @return
                     *     possible object is
                     *     {@link Ldml.Dates.TimeZoneNames.Metazone.Long.Daylight }
                     *     
                     */
                    public Ldml.Dates.TimeZoneNames.Metazone.Long.Daylight getDaylight() {
                        return daylight;
                    }

                    /**
                     * Define el valor de la propiedad daylight.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link Ldml.Dates.TimeZoneNames.Metazone.Long.Daylight }
                     *     
                     */
                    public void setDaylight(Ldml.Dates.TimeZoneNames.Metazone.Long.Daylight value) {
                        this.daylight = value;
                    }


                    /**
                     * <p>Clase Java para anonymous complex type.
                     * 
                     * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
                     * 
                     * <pre>
                     * &lt;complexType>
                     *   &lt;simpleContent>
                     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                     *       &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
                     *     &lt;/extension>
                     *   &lt;/simpleContent>
                     * &lt;/complexType>
                     * </pre>
                     * 
                     * 
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                        "value"
                    })
                    public static class Daylight {

                        @XmlValue
                        protected String value;
                        @XmlAttribute(name = "draft")
                        protected String draft;

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

                        /**
                         * Obtiene el valor de la propiedad draft.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getDraft() {
                            return draft;
                        }

                        /**
                         * Define el valor de la propiedad draft.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setDraft(String value) {
                            this.draft = value;
                        }

                    }


                    /**
                     * <p>Clase Java para anonymous complex type.
                     * 
                     * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
                     * 
                     * <pre>
                     * &lt;complexType>
                     *   &lt;simpleContent>
                     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                     *       &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
                     *     &lt;/extension>
                     *   &lt;/simpleContent>
                     * &lt;/complexType>
                     * </pre>
                     * 
                     * 
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                        "value"
                    })
                    public static class Generic {

                        @XmlValue
                        protected String value;
                        @XmlAttribute(name = "draft")
                        protected String draft;

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

                        /**
                         * Obtiene el valor de la propiedad draft.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getDraft() {
                            return draft;
                        }

                        /**
                         * Define el valor de la propiedad draft.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setDraft(String value) {
                            this.draft = value;
                        }

                    }


                    /**
                     * <p>Clase Java para anonymous complex type.
                     * 
                     * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
                     * 
                     * <pre>
                     * &lt;complexType>
                     *   &lt;simpleContent>
                     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                     *       &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
                     *     &lt;/extension>
                     *   &lt;/simpleContent>
                     * &lt;/complexType>
                     * </pre>
                     * 
                     * 
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                        "value"
                    })
                    public static class Standard {

                        @XmlValue
                        protected String value;
                        @XmlAttribute(name = "draft")
                        protected String draft;

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

                        /**
                         * Obtiene el valor de la propiedad draft.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getDraft() {
                            return draft;
                        }

                        /**
                         * Define el valor de la propiedad draft.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setDraft(String value) {
                            this.draft = value;
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
                 *         &lt;element name="generic" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
                 *         &lt;element name="standard" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *         &lt;element name="daylight" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
                    "generic",
                    "standard",
                    "daylight"
                })
                public static class Short {

                    protected String generic;
                    @XmlElement(required = true)
                    protected String standard;
                    protected String daylight;

                    /**
                     * Obtiene el valor de la propiedad generic.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getGeneric() {
                        return generic;
                    }

                    /**
                     * Define el valor de la propiedad generic.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setGeneric(String value) {
                        this.generic = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad standard.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getStandard() {
                        return standard;
                    }

                    /**
                     * Define el valor de la propiedad standard.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setStandard(String value) {
                        this.standard = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad daylight.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getDaylight() {
                        return daylight;
                    }

                    /**
                     * Define el valor de la propiedad daylight.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setDaylight(String value) {
                        this.daylight = value;
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
             *   &lt;simpleContent>
             *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/extension>
             *   &lt;/simpleContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "value"
            })
            public static class RegionFormat {

                @XmlValue
                protected String value;
                @XmlAttribute(name = "type")
                protected String type;

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

                /**
                 * Obtiene el valor de la propiedad type.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getType() {
                    return type;
                }

                /**
                 * Define el valor de la propiedad type.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setType(String value) {
                    this.type = value;
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
             *         &lt;element name="long" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="daylight" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="exemplarCity" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *       &lt;/sequence>
             *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "_long",
                "exemplarCity"
            })
            public static class Zone {

                @XmlElement(name = "long")
                protected Ldml.Dates.TimeZoneNames.Zone.Long _long;
                @XmlElement(required = true)
                protected String exemplarCity;
                @XmlAttribute(name = "type")
                protected String type;

                /**
                 * Obtiene el valor de la propiedad long.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Ldml.Dates.TimeZoneNames.Zone.Long }
                 *     
                 */
                public Ldml.Dates.TimeZoneNames.Zone.Long getLong() {
                    return _long;
                }

                /**
                 * Define el valor de la propiedad long.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Ldml.Dates.TimeZoneNames.Zone.Long }
                 *     
                 */
                public void setLong(Ldml.Dates.TimeZoneNames.Zone.Long value) {
                    this._long = value;
                }

                /**
                 * Obtiene el valor de la propiedad exemplarCity.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getExemplarCity() {
                    return exemplarCity;
                }

                /**
                 * Define el valor de la propiedad exemplarCity.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setExemplarCity(String value) {
                    this.exemplarCity = value;
                }

                /**
                 * Obtiene el valor de la propiedad type.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getType() {
                    return type;
                }

                /**
                 * Define el valor de la propiedad type.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setType(String value) {
                    this.type = value;
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
                 *         &lt;element name="daylight" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
                    "daylight"
                })
                public static class Long {

                    @XmlElement(required = true)
                    protected String daylight;

                    /**
                     * Obtiene el valor de la propiedad daylight.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getDaylight() {
                        return daylight;
                    }

                    /**
                     * Define el valor de la propiedad daylight.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setDaylight(String value) {
                        this.daylight = value;
                    }

                }

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
     *         &lt;element name="quotationStart" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="quotationEnd" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="alternateQuotationStart" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="alternateQuotationEnd" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "quotationStart",
        "quotationEnd",
        "alternateQuotationStart",
        "alternateQuotationEnd"
    })
    public static class Delimiters {

        @XmlElement(required = true)
        protected String quotationStart;
        @XmlElement(required = true)
        protected String quotationEnd;
        @XmlElement(required = true)
        protected String alternateQuotationStart;
        @XmlElement(required = true)
        protected String alternateQuotationEnd;

        /**
         * Obtiene el valor de la propiedad quotationStart.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getQuotationStart() {
            return quotationStart;
        }

        /**
         * Define el valor de la propiedad quotationStart.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setQuotationStart(String value) {
            this.quotationStart = value;
        }

        /**
         * Obtiene el valor de la propiedad quotationEnd.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getQuotationEnd() {
            return quotationEnd;
        }

        /**
         * Define el valor de la propiedad quotationEnd.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setQuotationEnd(String value) {
            this.quotationEnd = value;
        }

        /**
         * Obtiene el valor de la propiedad alternateQuotationStart.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAlternateQuotationStart() {
            return alternateQuotationStart;
        }

        /**
         * Define el valor de la propiedad alternateQuotationStart.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAlternateQuotationStart(String value) {
            this.alternateQuotationStart = value;
        }

        /**
         * Obtiene el valor de la propiedad alternateQuotationEnd.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAlternateQuotationEnd() {
            return alternateQuotationEnd;
        }

        /**
         * Define el valor de la propiedad alternateQuotationEnd.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAlternateQuotationEnd(String value) {
            this.alternateQuotationEnd = value;
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
     *         &lt;element name="version">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                 &lt;attribute name="number" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/extension>
     *             &lt;/simpleContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="generation">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                 &lt;attribute name="date" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/extension>
     *             &lt;/simpleContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="language">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/extension>
     *             &lt;/simpleContent>
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
        "version",
        "generation",
        "language"
    })
    public static class Identity {

        @XmlElement(required = true)
        protected Ldml.Identity.Version version;
        @XmlElement(required = true)
        protected Ldml.Identity.Generation generation;
        @XmlElement(required = true)
        protected Ldml.Identity.Language language;

        /**
         * Obtiene el valor de la propiedad version.
         * 
         * @return
         *     possible object is
         *     {@link Ldml.Identity.Version }
         *     
         */
        public Ldml.Identity.Version getVersion() {
            return version;
        }

        /**
         * Define el valor de la propiedad version.
         * 
         * @param value
         *     allowed object is
         *     {@link Ldml.Identity.Version }
         *     
         */
        public void setVersion(Ldml.Identity.Version value) {
            this.version = value;
        }

        /**
         * Obtiene el valor de la propiedad generation.
         * 
         * @return
         *     possible object is
         *     {@link Ldml.Identity.Generation }
         *     
         */
        public Ldml.Identity.Generation getGeneration() {
            return generation;
        }

        /**
         * Define el valor de la propiedad generation.
         * 
         * @param value
         *     allowed object is
         *     {@link Ldml.Identity.Generation }
         *     
         */
        public void setGeneration(Ldml.Identity.Generation value) {
            this.generation = value;
        }

        /**
         * Obtiene el valor de la propiedad language.
         * 
         * @return
         *     possible object is
         *     {@link Ldml.Identity.Language }
         *     
         */
        public Ldml.Identity.Language getLanguage() {
            return language;
        }

        /**
         * Define el valor de la propiedad language.
         * 
         * @param value
         *     allowed object is
         *     {@link Ldml.Identity.Language }
         *     
         */
        public void setLanguage(Ldml.Identity.Language value) {
            this.language = value;
        }


        /**
         * <p>Clase Java para anonymous complex type.
         * 
         * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *       &lt;attribute name="date" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class Generation {

            @XmlValue
            protected String value;
            @XmlAttribute(name = "date")
            protected String date;

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

            /**
             * Obtiene el valor de la propiedad date.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDate() {
                return date;
            }

            /**
             * Define el valor de la propiedad date.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDate(String value) {
                this.date = value;
            }

        }


        /**
         * <p>Clase Java para anonymous complex type.
         * 
         * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class Language {

            @XmlValue
            protected String value;
            @XmlAttribute(name = "type")
            protected String type;

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

            /**
             * Obtiene el valor de la propiedad type.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getType() {
                return type;
            }

            /**
             * Define el valor de la propiedad type.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setType(String value) {
                this.type = value;
            }

        }


        /**
         * <p>Clase Java para anonymous complex type.
         * 
         * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *       &lt;attribute name="number" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class Version {

            @XmlValue
            protected String value;
            @XmlAttribute(name = "number")
            protected String number;

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

            /**
             * Obtiene el valor de la propiedad number.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNumber() {
                return number;
            }

            /**
             * Define el valor de la propiedad number.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNumber(String value) {
                this.number = value;
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
     *         &lt;element name="listPattern" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="listPatternPart" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;simpleContent>
     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/extension>
     *                       &lt;/simpleContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
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
        "listPattern"
    })
    public static class ListPatterns {

        protected List<Ldml.ListPatterns.ListPattern> listPattern;

        /**
         * Gets the value of the listPattern property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the listPattern property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getListPattern().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Ldml.ListPatterns.ListPattern }
         * 
         * 
         */
        public List<Ldml.ListPatterns.ListPattern> getListPattern() {
            if (listPattern == null) {
                listPattern = new ArrayList<Ldml.ListPatterns.ListPattern>();
            }
            return this.listPattern;
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
         *         &lt;element name="listPatternPart" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;simpleContent>
         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
         *               &lt;/extension>
         *             &lt;/simpleContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "listPatternPart"
        })
        public static class ListPattern {

            protected List<Ldml.ListPatterns.ListPattern.ListPatternPart> listPatternPart;
            @XmlAttribute(name = "type")
            protected String type;

            /**
             * Gets the value of the listPatternPart property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the listPatternPart property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getListPatternPart().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Ldml.ListPatterns.ListPattern.ListPatternPart }
             * 
             * 
             */
            public List<Ldml.ListPatterns.ListPattern.ListPatternPart> getListPatternPart() {
                if (listPatternPart == null) {
                    listPatternPart = new ArrayList<Ldml.ListPatterns.ListPattern.ListPatternPart>();
                }
                return this.listPatternPart;
            }

            /**
             * Obtiene el valor de la propiedad type.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getType() {
                return type;
            }

            /**
             * Define el valor de la propiedad type.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setType(String value) {
                this.type = value;
            }


            /**
             * <p>Clase Java para anonymous complex type.
             * 
             * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;simpleContent>
             *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/extension>
             *   &lt;/simpleContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "value"
            })
            public static class ListPatternPart {

                @XmlValue
                protected String value;
                @XmlAttribute(name = "type")
                protected String type;

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

                /**
                 * Obtiene el valor de la propiedad type.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getType() {
                    return type;
                }

                /**
                 * Define el valor de la propiedad type.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setType(String value) {
                    this.type = value;
                }

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
     *         &lt;element name="localeDisplayPattern">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="localePattern" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="localeSeparator" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="localeKeyTypePattern" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="languages">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="language" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;simpleContent>
     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                           &lt;attribute name="alt" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                           &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/extension>
     *                       &lt;/simpleContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="scripts">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="script" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;simpleContent>
     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                           &lt;attribute name="alt" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                           &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/extension>
     *                       &lt;/simpleContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="territories">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="territory" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;simpleContent>
     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                           &lt;attribute name="alt" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/extension>
     *                       &lt;/simpleContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="variants">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="variant" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;simpleContent>
     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                           &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/extension>
     *                       &lt;/simpleContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="keys">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="key" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;simpleContent>
     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                           &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/extension>
     *                       &lt;/simpleContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="types">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="type" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;simpleContent>
     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                           &lt;attribute name="key" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                           &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/extension>
     *                       &lt;/simpleContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="transformNames">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="transformName" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;simpleContent>
     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                           &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/extension>
     *                       &lt;/simpleContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="measurementSystemNames">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="measurementSystemName" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;simpleContent>
     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/extension>
     *                       &lt;/simpleContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="codePatterns">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="codePattern" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;simpleContent>
     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/extension>
     *                       &lt;/simpleContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
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
        "localeDisplayPattern",
        "languages",
        "scripts",
        "territories",
        "variants",
        "keys",
        "types",
        "transformNames",
        "measurementSystemNames",
        "codePatterns"
    })
    public static class LocaleDisplayNames {

        @XmlElement(required = true)
        protected Ldml.LocaleDisplayNames.LocaleDisplayPattern localeDisplayPattern;
        @XmlElement(required = true)
        protected Ldml.LocaleDisplayNames.Languages languages;
        @XmlElement(required = true)
        protected Ldml.LocaleDisplayNames.Scripts scripts;
        @XmlElement(required = true)
        protected Ldml.LocaleDisplayNames.Territories territories;
        @XmlElement(required = true)
        protected Ldml.LocaleDisplayNames.Variants variants;
        @XmlElement(required = true)
        protected Ldml.LocaleDisplayNames.Keys keys;
        @XmlElement(required = true)
        protected Ldml.LocaleDisplayNames.Types types;
        @XmlElement(required = true)
        protected Ldml.LocaleDisplayNames.TransformNames transformNames;
        @XmlElement(required = true)
        protected Ldml.LocaleDisplayNames.MeasurementSystemNames measurementSystemNames;
        @XmlElement(required = true)
        protected Ldml.LocaleDisplayNames.CodePatterns codePatterns;

        /**
         * Obtiene el valor de la propiedad localeDisplayPattern.
         * 
         * @return
         *     possible object is
         *     {@link Ldml.LocaleDisplayNames.LocaleDisplayPattern }
         *     
         */
        public Ldml.LocaleDisplayNames.LocaleDisplayPattern getLocaleDisplayPattern() {
            return localeDisplayPattern;
        }

        /**
         * Define el valor de la propiedad localeDisplayPattern.
         * 
         * @param value
         *     allowed object is
         *     {@link Ldml.LocaleDisplayNames.LocaleDisplayPattern }
         *     
         */
        public void setLocaleDisplayPattern(Ldml.LocaleDisplayNames.LocaleDisplayPattern value) {
            this.localeDisplayPattern = value;
        }

        /**
         * Obtiene el valor de la propiedad languages.
         * 
         * @return
         *     possible object is
         *     {@link Ldml.LocaleDisplayNames.Languages }
         *     
         */
        public Ldml.LocaleDisplayNames.Languages getLanguages() {
            return languages;
        }

        /**
         * Define el valor de la propiedad languages.
         * 
         * @param value
         *     allowed object is
         *     {@link Ldml.LocaleDisplayNames.Languages }
         *     
         */
        public void setLanguages(Ldml.LocaleDisplayNames.Languages value) {
            this.languages = value;
        }

        /**
         * Obtiene el valor de la propiedad scripts.
         * 
         * @return
         *     possible object is
         *     {@link Ldml.LocaleDisplayNames.Scripts }
         *     
         */
        public Ldml.LocaleDisplayNames.Scripts getScripts() {
            return scripts;
        }

        /**
         * Define el valor de la propiedad scripts.
         * 
         * @param value
         *     allowed object is
         *     {@link Ldml.LocaleDisplayNames.Scripts }
         *     
         */
        public void setScripts(Ldml.LocaleDisplayNames.Scripts value) {
            this.scripts = value;
        }

        /**
         * Obtiene el valor de la propiedad territories.
         * 
         * @return
         *     possible object is
         *     {@link Ldml.LocaleDisplayNames.Territories }
         *     
         */
        public Ldml.LocaleDisplayNames.Territories getTerritories() {
            return territories;
        }

        /**
         * Define el valor de la propiedad territories.
         * 
         * @param value
         *     allowed object is
         *     {@link Ldml.LocaleDisplayNames.Territories }
         *     
         */
        public void setTerritories(Ldml.LocaleDisplayNames.Territories value) {
            this.territories = value;
        }

        /**
         * Obtiene el valor de la propiedad variants.
         * 
         * @return
         *     possible object is
         *     {@link Ldml.LocaleDisplayNames.Variants }
         *     
         */
        public Ldml.LocaleDisplayNames.Variants getVariants() {
            return variants;
        }

        /**
         * Define el valor de la propiedad variants.
         * 
         * @param value
         *     allowed object is
         *     {@link Ldml.LocaleDisplayNames.Variants }
         *     
         */
        public void setVariants(Ldml.LocaleDisplayNames.Variants value) {
            this.variants = value;
        }

        /**
         * Obtiene el valor de la propiedad keys.
         * 
         * @return
         *     possible object is
         *     {@link Ldml.LocaleDisplayNames.Keys }
         *     
         */
        public Ldml.LocaleDisplayNames.Keys getKeys() {
            return keys;
        }

        /**
         * Define el valor de la propiedad keys.
         * 
         * @param value
         *     allowed object is
         *     {@link Ldml.LocaleDisplayNames.Keys }
         *     
         */
        public void setKeys(Ldml.LocaleDisplayNames.Keys value) {
            this.keys = value;
        }

        /**
         * Obtiene el valor de la propiedad types.
         * 
         * @return
         *     possible object is
         *     {@link Ldml.LocaleDisplayNames.Types }
         *     
         */
        public Ldml.LocaleDisplayNames.Types getTypes() {
            return types;
        }

        /**
         * Define el valor de la propiedad types.
         * 
         * @param value
         *     allowed object is
         *     {@link Ldml.LocaleDisplayNames.Types }
         *     
         */
        public void setTypes(Ldml.LocaleDisplayNames.Types value) {
            this.types = value;
        }

        /**
         * Obtiene el valor de la propiedad transformNames.
         * 
         * @return
         *     possible object is
         *     {@link Ldml.LocaleDisplayNames.TransformNames }
         *     
         */
        public Ldml.LocaleDisplayNames.TransformNames getTransformNames() {
            return transformNames;
        }

        /**
         * Define el valor de la propiedad transformNames.
         * 
         * @param value
         *     allowed object is
         *     {@link Ldml.LocaleDisplayNames.TransformNames }
         *     
         */
        public void setTransformNames(Ldml.LocaleDisplayNames.TransformNames value) {
            this.transformNames = value;
        }

        /**
         * Obtiene el valor de la propiedad measurementSystemNames.
         * 
         * @return
         *     possible object is
         *     {@link Ldml.LocaleDisplayNames.MeasurementSystemNames }
         *     
         */
        public Ldml.LocaleDisplayNames.MeasurementSystemNames getMeasurementSystemNames() {
            return measurementSystemNames;
        }

        /**
         * Define el valor de la propiedad measurementSystemNames.
         * 
         * @param value
         *     allowed object is
         *     {@link Ldml.LocaleDisplayNames.MeasurementSystemNames }
         *     
         */
        public void setMeasurementSystemNames(Ldml.LocaleDisplayNames.MeasurementSystemNames value) {
            this.measurementSystemNames = value;
        }

        /**
         * Obtiene el valor de la propiedad codePatterns.
         * 
         * @return
         *     possible object is
         *     {@link Ldml.LocaleDisplayNames.CodePatterns }
         *     
         */
        public Ldml.LocaleDisplayNames.CodePatterns getCodePatterns() {
            return codePatterns;
        }

        /**
         * Define el valor de la propiedad codePatterns.
         * 
         * @param value
         *     allowed object is
         *     {@link Ldml.LocaleDisplayNames.CodePatterns }
         *     
         */
        public void setCodePatterns(Ldml.LocaleDisplayNames.CodePatterns value) {
            this.codePatterns = value;
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
         *         &lt;element name="codePattern" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;simpleContent>
         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
         *               &lt;/extension>
         *             &lt;/simpleContent>
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
            "codePattern"
        })
        public static class CodePatterns {

            protected List<Ldml.LocaleDisplayNames.CodePatterns.CodePattern> codePattern;

            /**
             * Gets the value of the codePattern property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the codePattern property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getCodePattern().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Ldml.LocaleDisplayNames.CodePatterns.CodePattern }
             * 
             * 
             */
            public List<Ldml.LocaleDisplayNames.CodePatterns.CodePattern> getCodePattern() {
                if (codePattern == null) {
                    codePattern = new ArrayList<Ldml.LocaleDisplayNames.CodePatterns.CodePattern>();
                }
                return this.codePattern;
            }


            /**
             * <p>Clase Java para anonymous complex type.
             * 
             * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;simpleContent>
             *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/extension>
             *   &lt;/simpleContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "value"
            })
            public static class CodePattern {

                @XmlValue
                protected String value;
                @XmlAttribute(name = "type")
                protected String type;

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

                /**
                 * Obtiene el valor de la propiedad type.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getType() {
                    return type;
                }

                /**
                 * Define el valor de la propiedad type.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setType(String value) {
                    this.type = value;
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
         *         &lt;element name="key" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;simpleContent>
         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                 &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
         *               &lt;/extension>
         *             &lt;/simpleContent>
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
            "key"
        })
        public static class Keys {

            protected List<Ldml.LocaleDisplayNames.Keys.Key> key;

            /**
             * Gets the value of the key property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the key property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getKey().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Ldml.LocaleDisplayNames.Keys.Key }
             * 
             * 
             */
            public List<Ldml.LocaleDisplayNames.Keys.Key> getKey() {
                if (key == null) {
                    key = new ArrayList<Ldml.LocaleDisplayNames.Keys.Key>();
                }
                return this.key;
            }


            /**
             * <p>Clase Java para anonymous complex type.
             * 
             * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;simpleContent>
             *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
             *       &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/extension>
             *   &lt;/simpleContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "value"
            })
            public static class Key {

                @XmlValue
                protected String value;
                @XmlAttribute(name = "type")
                protected String type;
                @XmlAttribute(name = "draft")
                protected String draft;

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

                /**
                 * Obtiene el valor de la propiedad type.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getType() {
                    return type;
                }

                /**
                 * Define el valor de la propiedad type.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setType(String value) {
                    this.type = value;
                }

                /**
                 * Obtiene el valor de la propiedad draft.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getDraft() {
                    return draft;
                }

                /**
                 * Define el valor de la propiedad draft.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setDraft(String value) {
                    this.draft = value;
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
         *         &lt;element name="language" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;simpleContent>
         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                 &lt;attribute name="alt" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                 &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
         *               &lt;/extension>
         *             &lt;/simpleContent>
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
            "language"
        })
        public static class Languages {

            protected List<Ldml.LocaleDisplayNames.Languages.Language> language;

            /**
             * Gets the value of the language property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the language property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getLanguage().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Ldml.LocaleDisplayNames.Languages.Language }
             * 
             * 
             */
            public List<Ldml.LocaleDisplayNames.Languages.Language> getLanguage() {
                if (language == null) {
                    language = new ArrayList<Ldml.LocaleDisplayNames.Languages.Language>();
                }
                return this.language;
            }


            /**
             * <p>Clase Java para anonymous complex type.
             * 
             * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;simpleContent>
             *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
             *       &lt;attribute name="alt" type="{http://www.w3.org/2001/XMLSchema}string" />
             *       &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/extension>
             *   &lt;/simpleContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "value"
            })
            public static class Language {

                @XmlValue
                protected String value;
                @XmlAttribute(name = "type")
                protected String type;
                @XmlAttribute(name = "alt")
                protected String alt;
                @XmlAttribute(name = "draft")
                protected String draft;

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

                /**
                 * Obtiene el valor de la propiedad type.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getType() {
                    return type;
                }

                /**
                 * Define el valor de la propiedad type.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setType(String value) {
                    this.type = value;
                }

                /**
                 * Obtiene el valor de la propiedad alt.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAlt() {
                    return alt;
                }

                /**
                 * Define el valor de la propiedad alt.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAlt(String value) {
                    this.alt = value;
                }

                /**
                 * Obtiene el valor de la propiedad draft.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getDraft() {
                    return draft;
                }

                /**
                 * Define el valor de la propiedad draft.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setDraft(String value) {
                    this.draft = value;
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
         *         &lt;element name="localePattern" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="localeSeparator" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="localeKeyTypePattern" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
            "localePattern",
            "localeSeparator",
            "localeKeyTypePattern"
        })
        public static class LocaleDisplayPattern {

            @XmlElement(required = true)
            protected String localePattern;
            @XmlElement(required = true)
            protected String localeSeparator;
            @XmlElement(required = true)
            protected String localeKeyTypePattern;

            /**
             * Obtiene el valor de la propiedad localePattern.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getLocalePattern() {
                return localePattern;
            }

            /**
             * Define el valor de la propiedad localePattern.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setLocalePattern(String value) {
                this.localePattern = value;
            }

            /**
             * Obtiene el valor de la propiedad localeSeparator.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getLocaleSeparator() {
                return localeSeparator;
            }

            /**
             * Define el valor de la propiedad localeSeparator.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setLocaleSeparator(String value) {
                this.localeSeparator = value;
            }

            /**
             * Obtiene el valor de la propiedad localeKeyTypePattern.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getLocaleKeyTypePattern() {
                return localeKeyTypePattern;
            }

            /**
             * Define el valor de la propiedad localeKeyTypePattern.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setLocaleKeyTypePattern(String value) {
                this.localeKeyTypePattern = value;
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
         *         &lt;element name="measurementSystemName" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;simpleContent>
         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
         *               &lt;/extension>
         *             &lt;/simpleContent>
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
            "measurementSystemName"
        })
        public static class MeasurementSystemNames {

            protected List<Ldml.LocaleDisplayNames.MeasurementSystemNames.MeasurementSystemName> measurementSystemName;

            /**
             * Gets the value of the measurementSystemName property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the measurementSystemName property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getMeasurementSystemName().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Ldml.LocaleDisplayNames.MeasurementSystemNames.MeasurementSystemName }
             * 
             * 
             */
            public List<Ldml.LocaleDisplayNames.MeasurementSystemNames.MeasurementSystemName> getMeasurementSystemName() {
                if (measurementSystemName == null) {
                    measurementSystemName = new ArrayList<Ldml.LocaleDisplayNames.MeasurementSystemNames.MeasurementSystemName>();
                }
                return this.measurementSystemName;
            }


            /**
             * <p>Clase Java para anonymous complex type.
             * 
             * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;simpleContent>
             *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/extension>
             *   &lt;/simpleContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "value"
            })
            public static class MeasurementSystemName {

                @XmlValue
                protected String value;
                @XmlAttribute(name = "type")
                protected String type;

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

                /**
                 * Obtiene el valor de la propiedad type.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getType() {
                    return type;
                }

                /**
                 * Define el valor de la propiedad type.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setType(String value) {
                    this.type = value;
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
         *         &lt;element name="script" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;simpleContent>
         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                 &lt;attribute name="alt" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                 &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
         *               &lt;/extension>
         *             &lt;/simpleContent>
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
            "script"
        })
        public static class Scripts {

            protected List<Ldml.LocaleDisplayNames.Scripts.Script> script;

            /**
             * Gets the value of the script property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the script property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getScript().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Ldml.LocaleDisplayNames.Scripts.Script }
             * 
             * 
             */
            public List<Ldml.LocaleDisplayNames.Scripts.Script> getScript() {
                if (script == null) {
                    script = new ArrayList<Ldml.LocaleDisplayNames.Scripts.Script>();
                }
                return this.script;
            }


            /**
             * <p>Clase Java para anonymous complex type.
             * 
             * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;simpleContent>
             *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
             *       &lt;attribute name="alt" type="{http://www.w3.org/2001/XMLSchema}string" />
             *       &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/extension>
             *   &lt;/simpleContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "value"
            })
            public static class Script {

                @XmlValue
                protected String value;
                @XmlAttribute(name = "type")
                protected String type;
                @XmlAttribute(name = "alt")
                protected String alt;
                @XmlAttribute(name = "draft")
                protected String draft;

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

                /**
                 * Obtiene el valor de la propiedad type.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getType() {
                    return type;
                }

                /**
                 * Define el valor de la propiedad type.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setType(String value) {
                    this.type = value;
                }

                /**
                 * Obtiene el valor de la propiedad alt.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAlt() {
                    return alt;
                }

                /**
                 * Define el valor de la propiedad alt.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAlt(String value) {
                    this.alt = value;
                }

                /**
                 * Obtiene el valor de la propiedad draft.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getDraft() {
                    return draft;
                }

                /**
                 * Define el valor de la propiedad draft.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setDraft(String value) {
                    this.draft = value;
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
         *         &lt;element name="territory" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;simpleContent>
         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                 &lt;attribute name="alt" type="{http://www.w3.org/2001/XMLSchema}string" />
         *               &lt;/extension>
         *             &lt;/simpleContent>
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
            "territory"
        })
        public static class Territories {

            protected List<Ldml.LocaleDisplayNames.Territories.Territory> territory;

            /**
             * Gets the value of the territory property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the territory property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getTerritory().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Ldml.LocaleDisplayNames.Territories.Territory }
             * 
             * 
             */
            public List<Ldml.LocaleDisplayNames.Territories.Territory> getTerritory() {
                if (territory == null) {
                    territory = new ArrayList<Ldml.LocaleDisplayNames.Territories.Territory>();
                }
                return this.territory;
            }


            /**
             * <p>Clase Java para anonymous complex type.
             * 
             * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;simpleContent>
             *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
             *       &lt;attribute name="alt" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/extension>
             *   &lt;/simpleContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "value"
            })
            public static class Territory {

                @XmlValue
                protected String value;
                @XmlAttribute(name = "type")
                protected String type;
                @XmlAttribute(name = "alt")
                protected String alt;

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

                /**
                 * Obtiene el valor de la propiedad type.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getType() {
                    return type;
                }

                /**
                 * Define el valor de la propiedad type.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setType(String value) {
                    this.type = value;
                }

                /**
                 * Obtiene el valor de la propiedad alt.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAlt() {
                    return alt;
                }

                /**
                 * Define el valor de la propiedad alt.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAlt(String value) {
                    this.alt = value;
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
         *         &lt;element name="transformName" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;simpleContent>
         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                 &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
         *               &lt;/extension>
         *             &lt;/simpleContent>
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
            "transformName"
        })
        public static class TransformNames {

            protected List<Ldml.LocaleDisplayNames.TransformNames.TransformName> transformName;

            /**
             * Gets the value of the transformName property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the transformName property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getTransformName().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Ldml.LocaleDisplayNames.TransformNames.TransformName }
             * 
             * 
             */
            public List<Ldml.LocaleDisplayNames.TransformNames.TransformName> getTransformName() {
                if (transformName == null) {
                    transformName = new ArrayList<Ldml.LocaleDisplayNames.TransformNames.TransformName>();
                }
                return this.transformName;
            }


            /**
             * <p>Clase Java para anonymous complex type.
             * 
             * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;simpleContent>
             *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
             *       &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/extension>
             *   &lt;/simpleContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "value"
            })
            public static class TransformName {

                @XmlValue
                protected String value;
                @XmlAttribute(name = "type")
                protected String type;
                @XmlAttribute(name = "draft")
                protected String draft;

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

                /**
                 * Obtiene el valor de la propiedad type.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getType() {
                    return type;
                }

                /**
                 * Define el valor de la propiedad type.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setType(String value) {
                    this.type = value;
                }

                /**
                 * Obtiene el valor de la propiedad draft.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getDraft() {
                    return draft;
                }

                /**
                 * Define el valor de la propiedad draft.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setDraft(String value) {
                    this.draft = value;
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
         *         &lt;element name="type" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;simpleContent>
         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                 &lt;attribute name="key" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                 &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
         *               &lt;/extension>
         *             &lt;/simpleContent>
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
            "type"
        })
        public static class Types {

            protected List<Ldml.LocaleDisplayNames.Types.Type> type;

            /**
             * Gets the value of the type property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the type property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getType().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Ldml.LocaleDisplayNames.Types.Type }
             * 
             * 
             */
            public List<Ldml.LocaleDisplayNames.Types.Type> getType() {
                if (type == null) {
                    type = new ArrayList<Ldml.LocaleDisplayNames.Types.Type>();
                }
                return this.type;
            }


            /**
             * <p>Clase Java para anonymous complex type.
             * 
             * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;simpleContent>
             *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
             *       &lt;attribute name="key" type="{http://www.w3.org/2001/XMLSchema}string" />
             *       &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/extension>
             *   &lt;/simpleContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "value"
            })
            public static class Type {

                @XmlValue
                protected String value;
                @XmlAttribute(name = "type")
                protected String type;
                @XmlAttribute(name = "key")
                protected String key;
                @XmlAttribute(name = "draft")
                protected String draft;

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

                /**
                 * Obtiene el valor de la propiedad type.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getType() {
                    return type;
                }

                /**
                 * Define el valor de la propiedad type.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setType(String value) {
                    this.type = value;
                }

                /**
                 * Obtiene el valor de la propiedad key.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getKey() {
                    return key;
                }

                /**
                 * Define el valor de la propiedad key.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setKey(String value) {
                    this.key = value;
                }

                /**
                 * Obtiene el valor de la propiedad draft.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getDraft() {
                    return draft;
                }

                /**
                 * Define el valor de la propiedad draft.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setDraft(String value) {
                    this.draft = value;
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
         *         &lt;element name="variant" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;simpleContent>
         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                 &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
         *               &lt;/extension>
         *             &lt;/simpleContent>
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
            "variant"
        })
        public static class Variants {

            protected List<Ldml.LocaleDisplayNames.Variants.Variant> variant;

            /**
             * Gets the value of the variant property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the variant property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getVariant().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Ldml.LocaleDisplayNames.Variants.Variant }
             * 
             * 
             */
            public List<Ldml.LocaleDisplayNames.Variants.Variant> getVariant() {
                if (variant == null) {
                    variant = new ArrayList<Ldml.LocaleDisplayNames.Variants.Variant>();
                }
                return this.variant;
            }


            /**
             * <p>Clase Java para anonymous complex type.
             * 
             * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;simpleContent>
             *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
             *       &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/extension>
             *   &lt;/simpleContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "value"
            })
            public static class Variant {

                @XmlValue
                protected String value;
                @XmlAttribute(name = "type")
                protected String type;
                @XmlAttribute(name = "draft")
                protected String draft;

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

                /**
                 * Obtiene el valor de la propiedad type.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getType() {
                    return type;
                }

                /**
                 * Define el valor de la propiedad type.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setType(String value) {
                    this.type = value;
                }

                /**
                 * Obtiene el valor de la propiedad draft.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getDraft() {
                    return draft;
                }

                /**
                 * Define el valor de la propiedad draft.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setDraft(String value) {
                    this.draft = value;
                }

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
     *         &lt;element name="defaultNumberingSystem">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                 &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/extension>
     *             &lt;/simpleContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="otherNumberingSystems">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="native">
     *                     &lt;complexType>
     *                       &lt;simpleContent>
     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                           &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/extension>
     *                       &lt;/simpleContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="minimumGroupingDigits">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>byte">
     *                 &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/extension>
     *             &lt;/simpleContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="symbols">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="decimal" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="group" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="list" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="percentSign" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="plusSign" type="{http://www.w3.org/2001/XMLSchema}byte"/>
     *                   &lt;element name="minusSign" type="{http://www.w3.org/2001/XMLSchema}byte"/>
     *                   &lt;element name="exponential" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="superscriptingExponent" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="perMille" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="infinity" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="nan" type="{http://www.w3.org/2001/XMLSchema}float"/>
     *                   &lt;element name="timeSeparator">
     *                     &lt;complexType>
     *                       &lt;simpleContent>
     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                           &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/extension>
     *                       &lt;/simpleContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *                 &lt;attribute name="numberSystem" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="decimalFormats">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="decimalFormatLength" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="decimalFormat">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="pattern" maxOccurs="unbounded" minOccurs="0">
     *                                         &lt;complexType>
     *                                           &lt;simpleContent>
     *                                             &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                                               &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}long" />
     *                                               &lt;attribute name="count" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                             &lt;/extension>
     *                                           &lt;/simpleContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *                 &lt;attribute name="numberSystem" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="scientificFormats">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="scientificFormatLength">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="scientificFormat">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="pattern" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *                 &lt;attribute name="numberSystem" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="percentFormats">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="percentFormatLength">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="percentFormat">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="pattern" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *                 &lt;attribute name="numberSystem" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="currencyFormats">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="currencyFormatLength">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="currencyFormat" maxOccurs="unbounded" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="pattern" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                     &lt;/sequence>
     *                                     &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="unitPattern" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;simpleContent>
     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                           &lt;attribute name="count" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/extension>
     *                       &lt;/simpleContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *                 &lt;attribute name="numberSystem" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="currencies">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="currency" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="displayName" maxOccurs="unbounded" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;simpleContent>
     *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                                     &lt;attribute name="count" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                     &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                   &lt;/extension>
     *                                 &lt;/simpleContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                             &lt;element name="symbol" maxOccurs="unbounded" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;simpleContent>
     *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                                     &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                     &lt;attribute name="alt" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                   &lt;/extension>
     *                                 &lt;/simpleContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="miscPatterns">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="pattern" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;simpleContent>
     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/extension>
     *                       &lt;/simpleContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *                 &lt;attribute name="numberSystem" type="{http://www.w3.org/2001/XMLSchema}string" />
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
        "defaultNumberingSystem",
        "otherNumberingSystems",
        "minimumGroupingDigits",
        "symbols",
        "decimalFormats",
        "scientificFormats",
        "percentFormats",
        "currencyFormats",
        "currencies",
        "miscPatterns"
    })
    public static class Numbers {

        @XmlElement(required = true)
        protected Ldml.Numbers.DefaultNumberingSystem defaultNumberingSystem;
        @XmlElement(required = true)
        protected Ldml.Numbers.OtherNumberingSystems otherNumberingSystems;
        @XmlElement(required = true)
        protected Ldml.Numbers.MinimumGroupingDigits minimumGroupingDigits;
        @XmlElement(required = true)
        protected Ldml.Numbers.Symbols symbols;
        @XmlElement(required = true)
        protected Ldml.Numbers.DecimalFormats decimalFormats;
        @XmlElement(required = true)
        protected Ldml.Numbers.ScientificFormats scientificFormats;
        @XmlElement(required = true)
        protected Ldml.Numbers.PercentFormats percentFormats;
        @XmlElement(required = true)
        protected Ldml.Numbers.CurrencyFormats currencyFormats;
        @XmlElement(required = true)
        protected Ldml.Numbers.Currencies currencies;
        @XmlElement(required = true)
        protected Ldml.Numbers.MiscPatterns miscPatterns;

        /**
         * Obtiene el valor de la propiedad defaultNumberingSystem.
         * 
         * @return
         *     possible object is
         *     {@link Ldml.Numbers.DefaultNumberingSystem }
         *     
         */
        public Ldml.Numbers.DefaultNumberingSystem getDefaultNumberingSystem() {
            return defaultNumberingSystem;
        }

        /**
         * Define el valor de la propiedad defaultNumberingSystem.
         * 
         * @param value
         *     allowed object is
         *     {@link Ldml.Numbers.DefaultNumberingSystem }
         *     
         */
        public void setDefaultNumberingSystem(Ldml.Numbers.DefaultNumberingSystem value) {
            this.defaultNumberingSystem = value;
        }

        /**
         * Obtiene el valor de la propiedad otherNumberingSystems.
         * 
         * @return
         *     possible object is
         *     {@link Ldml.Numbers.OtherNumberingSystems }
         *     
         */
        public Ldml.Numbers.OtherNumberingSystems getOtherNumberingSystems() {
            return otherNumberingSystems;
        }

        /**
         * Define el valor de la propiedad otherNumberingSystems.
         * 
         * @param value
         *     allowed object is
         *     {@link Ldml.Numbers.OtherNumberingSystems }
         *     
         */
        public void setOtherNumberingSystems(Ldml.Numbers.OtherNumberingSystems value) {
            this.otherNumberingSystems = value;
        }

        /**
         * Obtiene el valor de la propiedad minimumGroupingDigits.
         * 
         * @return
         *     possible object is
         *     {@link Ldml.Numbers.MinimumGroupingDigits }
         *     
         */
        public Ldml.Numbers.MinimumGroupingDigits getMinimumGroupingDigits() {
            return minimumGroupingDigits;
        }

        /**
         * Define el valor de la propiedad minimumGroupingDigits.
         * 
         * @param value
         *     allowed object is
         *     {@link Ldml.Numbers.MinimumGroupingDigits }
         *     
         */
        public void setMinimumGroupingDigits(Ldml.Numbers.MinimumGroupingDigits value) {
            this.minimumGroupingDigits = value;
        }

        /**
         * Obtiene el valor de la propiedad symbols.
         * 
         * @return
         *     possible object is
         *     {@link Ldml.Numbers.Symbols }
         *     
         */
        public Ldml.Numbers.Symbols getSymbols() {
            return symbols;
        }

        /**
         * Define el valor de la propiedad symbols.
         * 
         * @param value
         *     allowed object is
         *     {@link Ldml.Numbers.Symbols }
         *     
         */
        public void setSymbols(Ldml.Numbers.Symbols value) {
            this.symbols = value;
        }

        /**
         * Obtiene el valor de la propiedad decimalFormats.
         * 
         * @return
         *     possible object is
         *     {@link Ldml.Numbers.DecimalFormats }
         *     
         */
        public Ldml.Numbers.DecimalFormats getDecimalFormats() {
            return decimalFormats;
        }

        /**
         * Define el valor de la propiedad decimalFormats.
         * 
         * @param value
         *     allowed object is
         *     {@link Ldml.Numbers.DecimalFormats }
         *     
         */
        public void setDecimalFormats(Ldml.Numbers.DecimalFormats value) {
            this.decimalFormats = value;
        }

        /**
         * Obtiene el valor de la propiedad scientificFormats.
         * 
         * @return
         *     possible object is
         *     {@link Ldml.Numbers.ScientificFormats }
         *     
         */
        public Ldml.Numbers.ScientificFormats getScientificFormats() {
            return scientificFormats;
        }

        /**
         * Define el valor de la propiedad scientificFormats.
         * 
         * @param value
         *     allowed object is
         *     {@link Ldml.Numbers.ScientificFormats }
         *     
         */
        public void setScientificFormats(Ldml.Numbers.ScientificFormats value) {
            this.scientificFormats = value;
        }

        /**
         * Obtiene el valor de la propiedad percentFormats.
         * 
         * @return
         *     possible object is
         *     {@link Ldml.Numbers.PercentFormats }
         *     
         */
        public Ldml.Numbers.PercentFormats getPercentFormats() {
            return percentFormats;
        }

        /**
         * Define el valor de la propiedad percentFormats.
         * 
         * @param value
         *     allowed object is
         *     {@link Ldml.Numbers.PercentFormats }
         *     
         */
        public void setPercentFormats(Ldml.Numbers.PercentFormats value) {
            this.percentFormats = value;
        }

        /**
         * Obtiene el valor de la propiedad currencyFormats.
         * 
         * @return
         *     possible object is
         *     {@link Ldml.Numbers.CurrencyFormats }
         *     
         */
        public Ldml.Numbers.CurrencyFormats getCurrencyFormats() {
            return currencyFormats;
        }

        /**
         * Define el valor de la propiedad currencyFormats.
         * 
         * @param value
         *     allowed object is
         *     {@link Ldml.Numbers.CurrencyFormats }
         *     
         */
        public void setCurrencyFormats(Ldml.Numbers.CurrencyFormats value) {
            this.currencyFormats = value;
        }

        /**
         * Obtiene el valor de la propiedad currencies.
         * 
         * @return
         *     possible object is
         *     {@link Ldml.Numbers.Currencies }
         *     
         */
        public Ldml.Numbers.Currencies getCurrencies() {
            return currencies;
        }

        /**
         * Define el valor de la propiedad currencies.
         * 
         * @param value
         *     allowed object is
         *     {@link Ldml.Numbers.Currencies }
         *     
         */
        public void setCurrencies(Ldml.Numbers.Currencies value) {
            this.currencies = value;
        }

        /**
         * Obtiene el valor de la propiedad miscPatterns.
         * 
         * @return
         *     possible object is
         *     {@link Ldml.Numbers.MiscPatterns }
         *     
         */
        public Ldml.Numbers.MiscPatterns getMiscPatterns() {
            return miscPatterns;
        }

        /**
         * Define el valor de la propiedad miscPatterns.
         * 
         * @param value
         *     allowed object is
         *     {@link Ldml.Numbers.MiscPatterns }
         *     
         */
        public void setMiscPatterns(Ldml.Numbers.MiscPatterns value) {
            this.miscPatterns = value;
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
         *         &lt;element name="currency" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="displayName" maxOccurs="unbounded" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;simpleContent>
         *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                           &lt;attribute name="count" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                           &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                         &lt;/extension>
         *                       &lt;/simpleContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                   &lt;element name="symbol" maxOccurs="unbounded" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;simpleContent>
         *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                           &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                           &lt;attribute name="alt" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                         &lt;/extension>
         *                       &lt;/simpleContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
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
            "currency"
        })
        public static class Currencies {

            protected List<Ldml.Numbers.Currencies.Currency> currency;

            /**
             * Gets the value of the currency property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the currency property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getCurrency().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Ldml.Numbers.Currencies.Currency }
             * 
             * 
             */
            public List<Ldml.Numbers.Currencies.Currency> getCurrency() {
                if (currency == null) {
                    currency = new ArrayList<Ldml.Numbers.Currencies.Currency>();
                }
                return this.currency;
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
             *         &lt;element name="displayName" maxOccurs="unbounded" minOccurs="0">
             *           &lt;complexType>
             *             &lt;simpleContent>
             *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *                 &lt;attribute name="count" type="{http://www.w3.org/2001/XMLSchema}string" />
             *                 &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
             *               &lt;/extension>
             *             &lt;/simpleContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="symbol" maxOccurs="unbounded" minOccurs="0">
             *           &lt;complexType>
             *             &lt;simpleContent>
             *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *                 &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
             *                 &lt;attribute name="alt" type="{http://www.w3.org/2001/XMLSchema}string" />
             *               &lt;/extension>
             *             &lt;/simpleContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *       &lt;/sequence>
             *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "displayName",
                "symbol"
            })
            public static class Currency {

                protected List<Ldml.Numbers.Currencies.Currency.DisplayName> displayName;
                protected List<Ldml.Numbers.Currencies.Currency.Symbol> symbol;
                @XmlAttribute(name = "type")
                protected String type;

                /**
                 * Gets the value of the displayName property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the displayName property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getDisplayName().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link Ldml.Numbers.Currencies.Currency.DisplayName }
                 * 
                 * 
                 */
                public List<Ldml.Numbers.Currencies.Currency.DisplayName> getDisplayName() {
                    if (displayName == null) {
                        displayName = new ArrayList<Ldml.Numbers.Currencies.Currency.DisplayName>();
                    }
                    return this.displayName;
                }

                /**
                 * Gets the value of the symbol property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the symbol property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getSymbol().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link Ldml.Numbers.Currencies.Currency.Symbol }
                 * 
                 * 
                 */
                public List<Ldml.Numbers.Currencies.Currency.Symbol> getSymbol() {
                    if (symbol == null) {
                        symbol = new ArrayList<Ldml.Numbers.Currencies.Currency.Symbol>();
                    }
                    return this.symbol;
                }

                /**
                 * Obtiene el valor de la propiedad type.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getType() {
                    return type;
                }

                /**
                 * Define el valor de la propiedad type.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setType(String value) {
                    this.type = value;
                }


                /**
                 * <p>Clase Java para anonymous complex type.
                 * 
                 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;simpleContent>
                 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                 *       &lt;attribute name="count" type="{http://www.w3.org/2001/XMLSchema}string" />
                 *       &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
                 *     &lt;/extension>
                 *   &lt;/simpleContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "value"
                })
                public static class DisplayName {

                    @XmlValue
                    protected String value;
                    @XmlAttribute(name = "count")
                    protected String count;
                    @XmlAttribute(name = "draft")
                    protected String draft;

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

                    /**
                     * Obtiene el valor de la propiedad count.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getCount() {
                        return count;
                    }

                    /**
                     * Define el valor de la propiedad count.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setCount(String value) {
                        this.count = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad draft.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getDraft() {
                        return draft;
                    }

                    /**
                     * Define el valor de la propiedad draft.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setDraft(String value) {
                        this.draft = value;
                    }

                }


                /**
                 * <p>Clase Java para anonymous complex type.
                 * 
                 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;simpleContent>
                 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                 *       &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
                 *       &lt;attribute name="alt" type="{http://www.w3.org/2001/XMLSchema}string" />
                 *     &lt;/extension>
                 *   &lt;/simpleContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "value"
                })
                public static class Symbol {

                    @XmlValue
                    protected String value;
                    @XmlAttribute(name = "draft")
                    protected String draft;
                    @XmlAttribute(name = "alt")
                    protected String alt;

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

                    /**
                     * Obtiene el valor de la propiedad draft.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getDraft() {
                        return draft;
                    }

                    /**
                     * Define el valor de la propiedad draft.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setDraft(String value) {
                        this.draft = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad alt.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getAlt() {
                        return alt;
                    }

                    /**
                     * Define el valor de la propiedad alt.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setAlt(String value) {
                        this.alt = value;
                    }

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
         *         &lt;element name="currencyFormatLength">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="currencyFormat" maxOccurs="unbounded" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="pattern" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                           &lt;/sequence>
         *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="unitPattern" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;simpleContent>
         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                 &lt;attribute name="count" type="{http://www.w3.org/2001/XMLSchema}string" />
         *               &lt;/extension>
         *             &lt;/simpleContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *       &lt;attribute name="numberSystem" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "currencyFormatLength",
            "unitPattern"
        })
        public static class CurrencyFormats {

            @XmlElement(required = true)
            protected Ldml.Numbers.CurrencyFormats.CurrencyFormatLength currencyFormatLength;
            protected List<Ldml.Numbers.CurrencyFormats.UnitPattern> unitPattern;
            @XmlAttribute(name = "numberSystem")
            protected String numberSystem;

            /**
             * Obtiene el valor de la propiedad currencyFormatLength.
             * 
             * @return
             *     possible object is
             *     {@link Ldml.Numbers.CurrencyFormats.CurrencyFormatLength }
             *     
             */
            public Ldml.Numbers.CurrencyFormats.CurrencyFormatLength getCurrencyFormatLength() {
                return currencyFormatLength;
            }

            /**
             * Define el valor de la propiedad currencyFormatLength.
             * 
             * @param value
             *     allowed object is
             *     {@link Ldml.Numbers.CurrencyFormats.CurrencyFormatLength }
             *     
             */
            public void setCurrencyFormatLength(Ldml.Numbers.CurrencyFormats.CurrencyFormatLength value) {
                this.currencyFormatLength = value;
            }

            /**
             * Gets the value of the unitPattern property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the unitPattern property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getUnitPattern().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Ldml.Numbers.CurrencyFormats.UnitPattern }
             * 
             * 
             */
            public List<Ldml.Numbers.CurrencyFormats.UnitPattern> getUnitPattern() {
                if (unitPattern == null) {
                    unitPattern = new ArrayList<Ldml.Numbers.CurrencyFormats.UnitPattern>();
                }
                return this.unitPattern;
            }

            /**
             * Obtiene el valor de la propiedad numberSystem.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNumberSystem() {
                return numberSystem;
            }

            /**
             * Define el valor de la propiedad numberSystem.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNumberSystem(String value) {
                this.numberSystem = value;
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
             *         &lt;element name="currencyFormat" maxOccurs="unbounded" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="pattern" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                 &lt;/sequence>
             *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
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
                "currencyFormat"
            })
            public static class CurrencyFormatLength {

                protected List<Ldml.Numbers.CurrencyFormats.CurrencyFormatLength.CurrencyFormat> currencyFormat;

                /**
                 * Gets the value of the currencyFormat property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the currencyFormat property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getCurrencyFormat().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link Ldml.Numbers.CurrencyFormats.CurrencyFormatLength.CurrencyFormat }
                 * 
                 * 
                 */
                public List<Ldml.Numbers.CurrencyFormats.CurrencyFormatLength.CurrencyFormat> getCurrencyFormat() {
                    if (currencyFormat == null) {
                        currencyFormat = new ArrayList<Ldml.Numbers.CurrencyFormats.CurrencyFormatLength.CurrencyFormat>();
                    }
                    return this.currencyFormat;
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
                 *         &lt;element name="pattern" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *       &lt;/sequence>
                 *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "pattern"
                })
                public static class CurrencyFormat {

                    @XmlElement(required = true)
                    protected String pattern;
                    @XmlAttribute(name = "type")
                    protected String type;

                    /**
                     * Obtiene el valor de la propiedad pattern.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getPattern() {
                        return pattern;
                    }

                    /**
                     * Define el valor de la propiedad pattern.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setPattern(String value) {
                        this.pattern = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad type.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getType() {
                        return type;
                    }

                    /**
                     * Define el valor de la propiedad type.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setType(String value) {
                        this.type = value;
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
             *   &lt;simpleContent>
             *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *       &lt;attribute name="count" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/extension>
             *   &lt;/simpleContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "value"
            })
            public static class UnitPattern {

                @XmlValue
                protected String value;
                @XmlAttribute(name = "count")
                protected String count;

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

                /**
                 * Obtiene el valor de la propiedad count.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getCount() {
                    return count;
                }

                /**
                 * Define el valor de la propiedad count.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setCount(String value) {
                    this.count = value;
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
         *         &lt;element name="decimalFormatLength" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="decimalFormat">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="pattern" maxOccurs="unbounded" minOccurs="0">
         *                               &lt;complexType>
         *                                 &lt;simpleContent>
         *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                                     &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}long" />
         *                                     &lt;attribute name="count" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                                   &lt;/extension>
         *                                 &lt;/simpleContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *       &lt;attribute name="numberSystem" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "decimalFormatLength"
        })
        public static class DecimalFormats {

            protected List<Ldml.Numbers.DecimalFormats.DecimalFormatLength> decimalFormatLength;
            @XmlAttribute(name = "numberSystem")
            protected String numberSystem;

            /**
             * Gets the value of the decimalFormatLength property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the decimalFormatLength property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getDecimalFormatLength().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Ldml.Numbers.DecimalFormats.DecimalFormatLength }
             * 
             * 
             */
            public List<Ldml.Numbers.DecimalFormats.DecimalFormatLength> getDecimalFormatLength() {
                if (decimalFormatLength == null) {
                    decimalFormatLength = new ArrayList<Ldml.Numbers.DecimalFormats.DecimalFormatLength>();
                }
                return this.decimalFormatLength;
            }

            /**
             * Obtiene el valor de la propiedad numberSystem.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNumberSystem() {
                return numberSystem;
            }

            /**
             * Define el valor de la propiedad numberSystem.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNumberSystem(String value) {
                this.numberSystem = value;
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
             *         &lt;element name="decimalFormat">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="pattern" maxOccurs="unbounded" minOccurs="0">
             *                     &lt;complexType>
             *                       &lt;simpleContent>
             *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}long" />
             *                           &lt;attribute name="count" type="{http://www.w3.org/2001/XMLSchema}string" />
             *                         &lt;/extension>
             *                       &lt;/simpleContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *       &lt;/sequence>
             *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "decimalFormat"
            })
            public static class DecimalFormatLength {

                @XmlElement(required = true)
                protected Ldml.Numbers.DecimalFormats.DecimalFormatLength.DecimalFormat decimalFormat;
                @XmlAttribute(name = "type")
                protected String type;

                /**
                 * Obtiene el valor de la propiedad decimalFormat.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Ldml.Numbers.DecimalFormats.DecimalFormatLength.DecimalFormat }
                 *     
                 */
                public Ldml.Numbers.DecimalFormats.DecimalFormatLength.DecimalFormat getDecimalFormat() {
                    return decimalFormat;
                }

                /**
                 * Define el valor de la propiedad decimalFormat.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Ldml.Numbers.DecimalFormats.DecimalFormatLength.DecimalFormat }
                 *     
                 */
                public void setDecimalFormat(Ldml.Numbers.DecimalFormats.DecimalFormatLength.DecimalFormat value) {
                    this.decimalFormat = value;
                }

                /**
                 * Obtiene el valor de la propiedad type.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getType() {
                    return type;
                }

                /**
                 * Define el valor de la propiedad type.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setType(String value) {
                    this.type = value;
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
                 *         &lt;element name="pattern" maxOccurs="unbounded" minOccurs="0">
                 *           &lt;complexType>
                 *             &lt;simpleContent>
                 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                 *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}long" />
                 *                 &lt;attribute name="count" type="{http://www.w3.org/2001/XMLSchema}string" />
                 *               &lt;/extension>
                 *             &lt;/simpleContent>
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
                    "pattern"
                })
                public static class DecimalFormat {

                    protected List<Ldml.Numbers.DecimalFormats.DecimalFormatLength.DecimalFormat.Pattern> pattern;

                    /**
                     * Gets the value of the pattern property.
                     * 
                     * <p>
                     * This accessor method returns a reference to the live list,
                     * not a snapshot. Therefore any modification you make to the
                     * returned list will be present inside the JAXB object.
                     * This is why there is not a <CODE>set</CODE> method for the pattern property.
                     * 
                     * <p>
                     * For example, to add a new item, do as follows:
                     * <pre>
                     *    getPattern().add(newItem);
                     * </pre>
                     * 
                     * 
                     * <p>
                     * Objects of the following type(s) are allowed in the list
                     * {@link Ldml.Numbers.DecimalFormats.DecimalFormatLength.DecimalFormat.Pattern }
                     * 
                     * 
                     */
                    public List<Ldml.Numbers.DecimalFormats.DecimalFormatLength.DecimalFormat.Pattern> getPattern() {
                        if (pattern == null) {
                            pattern = new ArrayList<Ldml.Numbers.DecimalFormats.DecimalFormatLength.DecimalFormat.Pattern>();
                        }
                        return this.pattern;
                    }


                    /**
                     * <p>Clase Java para anonymous complex type.
                     * 
                     * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
                     * 
                     * <pre>
                     * &lt;complexType>
                     *   &lt;simpleContent>
                     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                     *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}long" />
                     *       &lt;attribute name="count" type="{http://www.w3.org/2001/XMLSchema}string" />
                     *     &lt;/extension>
                     *   &lt;/simpleContent>
                     * &lt;/complexType>
                     * </pre>
                     * 
                     * 
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                        "value"
                    })
                    public static class Pattern {

                        @XmlValue
                        protected String value;
                        @XmlAttribute(name = "type")
                        protected java.lang.Long type;
                        @XmlAttribute(name = "count")
                        protected String count;

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

                        /**
                         * Obtiene el valor de la propiedad type.
                         * 
                         * @return
                         *     possible object is
                         *     {@link java.lang.Long }
                         *     
                         */
                        public java.lang.Long getType() {
                            return type;
                        }

                        /**
                         * Define el valor de la propiedad type.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link java.lang.Long }
                         *     
                         */
                        public void setType(java.lang.Long value) {
                            this.type = value;
                        }

                        /**
                         * Obtiene el valor de la propiedad count.
                         * 
                         * @return
                         *     possible object is
                         *     {@link String }
                         *     
                         */
                        public String getCount() {
                            return count;
                        }

                        /**
                         * Define el valor de la propiedad count.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link String }
                         *     
                         */
                        public void setCount(String value) {
                            this.count = value;
                        }

                    }

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
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *       &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class DefaultNumberingSystem {

            @XmlValue
            protected String value;
            @XmlAttribute(name = "draft")
            protected String draft;

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

            /**
             * Obtiene el valor de la propiedad draft.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDraft() {
                return draft;
            }

            /**
             * Define el valor de la propiedad draft.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDraft(String value) {
                this.draft = value;
            }

        }


        /**
         * <p>Clase Java para anonymous complex type.
         * 
         * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>byte">
         *       &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class MinimumGroupingDigits {

            @XmlValue
            protected byte value;
            @XmlAttribute(name = "draft")
            protected String draft;

            /**
             * Obtiene el valor de la propiedad value.
             * 
             */
            public byte getValue() {
                return value;
            }

            /**
             * Define el valor de la propiedad value.
             * 
             */
            public void setValue(byte value) {
                this.value = value;
            }

            /**
             * Obtiene el valor de la propiedad draft.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDraft() {
                return draft;
            }

            /**
             * Define el valor de la propiedad draft.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDraft(String value) {
                this.draft = value;
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
         *         &lt;element name="pattern" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;simpleContent>
         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
         *               &lt;/extension>
         *             &lt;/simpleContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *       &lt;attribute name="numberSystem" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "pattern"
        })
        public static class MiscPatterns {

            protected List<Ldml.Numbers.MiscPatterns.Pattern> pattern;
            @XmlAttribute(name = "numberSystem")
            protected String numberSystem;

            /**
             * Gets the value of the pattern property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the pattern property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getPattern().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Ldml.Numbers.MiscPatterns.Pattern }
             * 
             * 
             */
            public List<Ldml.Numbers.MiscPatterns.Pattern> getPattern() {
                if (pattern == null) {
                    pattern = new ArrayList<Ldml.Numbers.MiscPatterns.Pattern>();
                }
                return this.pattern;
            }

            /**
             * Obtiene el valor de la propiedad numberSystem.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNumberSystem() {
                return numberSystem;
            }

            /**
             * Define el valor de la propiedad numberSystem.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNumberSystem(String value) {
                this.numberSystem = value;
            }


            /**
             * <p>Clase Java para anonymous complex type.
             * 
             * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;simpleContent>
             *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/extension>
             *   &lt;/simpleContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "value"
            })
            public static class Pattern {

                @XmlValue
                protected String value;
                @XmlAttribute(name = "type")
                protected String type;

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

                /**
                 * Obtiene el valor de la propiedad type.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getType() {
                    return type;
                }

                /**
                 * Define el valor de la propiedad type.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setType(String value) {
                    this.type = value;
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
         *         &lt;element name="native">
         *           &lt;complexType>
         *             &lt;simpleContent>
         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                 &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
         *               &lt;/extension>
         *             &lt;/simpleContent>
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
            "_native"
        })
        public static class OtherNumberingSystems {

            @XmlElement(name = "native", required = true)
            protected Ldml.Numbers.OtherNumberingSystems.Native _native;

            /**
             * Obtiene el valor de la propiedad native.
             * 
             * @return
             *     possible object is
             *     {@link Ldml.Numbers.OtherNumberingSystems.Native }
             *     
             */
            public Ldml.Numbers.OtherNumberingSystems.Native getNative() {
                return _native;
            }

            /**
             * Define el valor de la propiedad native.
             * 
             * @param value
             *     allowed object is
             *     {@link Ldml.Numbers.OtherNumberingSystems.Native }
             *     
             */
            public void setNative(Ldml.Numbers.OtherNumberingSystems.Native value) {
                this._native = value;
            }


            /**
             * <p>Clase Java para anonymous complex type.
             * 
             * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;simpleContent>
             *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *       &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/extension>
             *   &lt;/simpleContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "value"
            })
            public static class Native {

                @XmlValue
                protected String value;
                @XmlAttribute(name = "draft")
                protected String draft;

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

                /**
                 * Obtiene el valor de la propiedad draft.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getDraft() {
                    return draft;
                }

                /**
                 * Define el valor de la propiedad draft.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setDraft(String value) {
                    this.draft = value;
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
         *         &lt;element name="percentFormatLength">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="percentFormat">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="pattern" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *       &lt;attribute name="numberSystem" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "percentFormatLength"
        })
        public static class PercentFormats {

            @XmlElement(required = true)
            protected Ldml.Numbers.PercentFormats.PercentFormatLength percentFormatLength;
            @XmlAttribute(name = "numberSystem")
            protected String numberSystem;

            /**
             * Obtiene el valor de la propiedad percentFormatLength.
             * 
             * @return
             *     possible object is
             *     {@link Ldml.Numbers.PercentFormats.PercentFormatLength }
             *     
             */
            public Ldml.Numbers.PercentFormats.PercentFormatLength getPercentFormatLength() {
                return percentFormatLength;
            }

            /**
             * Define el valor de la propiedad percentFormatLength.
             * 
             * @param value
             *     allowed object is
             *     {@link Ldml.Numbers.PercentFormats.PercentFormatLength }
             *     
             */
            public void setPercentFormatLength(Ldml.Numbers.PercentFormats.PercentFormatLength value) {
                this.percentFormatLength = value;
            }

            /**
             * Obtiene el valor de la propiedad numberSystem.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNumberSystem() {
                return numberSystem;
            }

            /**
             * Define el valor de la propiedad numberSystem.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNumberSystem(String value) {
                this.numberSystem = value;
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
             *         &lt;element name="percentFormat">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="pattern" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
                "percentFormat"
            })
            public static class PercentFormatLength {

                @XmlElement(required = true)
                protected Ldml.Numbers.PercentFormats.PercentFormatLength.PercentFormat percentFormat;

                /**
                 * Obtiene el valor de la propiedad percentFormat.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Ldml.Numbers.PercentFormats.PercentFormatLength.PercentFormat }
                 *     
                 */
                public Ldml.Numbers.PercentFormats.PercentFormatLength.PercentFormat getPercentFormat() {
                    return percentFormat;
                }

                /**
                 * Define el valor de la propiedad percentFormat.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Ldml.Numbers.PercentFormats.PercentFormatLength.PercentFormat }
                 *     
                 */
                public void setPercentFormat(Ldml.Numbers.PercentFormats.PercentFormatLength.PercentFormat value) {
                    this.percentFormat = value;
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
                 *         &lt;element name="pattern" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
                    "pattern"
                })
                public static class PercentFormat {

                    @XmlElement(required = true)
                    protected String pattern;

                    /**
                     * Obtiene el valor de la propiedad pattern.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getPattern() {
                        return pattern;
                    }

                    /**
                     * Define el valor de la propiedad pattern.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setPattern(String value) {
                        this.pattern = value;
                    }

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
         *         &lt;element name="scientificFormatLength">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="scientificFormat">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="pattern" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *       &lt;attribute name="numberSystem" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "scientificFormatLength"
        })
        public static class ScientificFormats {

            @XmlElement(required = true)
            protected Ldml.Numbers.ScientificFormats.ScientificFormatLength scientificFormatLength;
            @XmlAttribute(name = "numberSystem")
            protected String numberSystem;

            /**
             * Obtiene el valor de la propiedad scientificFormatLength.
             * 
             * @return
             *     possible object is
             *     {@link Ldml.Numbers.ScientificFormats.ScientificFormatLength }
             *     
             */
            public Ldml.Numbers.ScientificFormats.ScientificFormatLength getScientificFormatLength() {
                return scientificFormatLength;
            }

            /**
             * Define el valor de la propiedad scientificFormatLength.
             * 
             * @param value
             *     allowed object is
             *     {@link Ldml.Numbers.ScientificFormats.ScientificFormatLength }
             *     
             */
            public void setScientificFormatLength(Ldml.Numbers.ScientificFormats.ScientificFormatLength value) {
                this.scientificFormatLength = value;
            }

            /**
             * Obtiene el valor de la propiedad numberSystem.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNumberSystem() {
                return numberSystem;
            }

            /**
             * Define el valor de la propiedad numberSystem.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNumberSystem(String value) {
                this.numberSystem = value;
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
             *         &lt;element name="scientificFormat">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="pattern" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
                "scientificFormat"
            })
            public static class ScientificFormatLength {

                @XmlElement(required = true)
                protected Ldml.Numbers.ScientificFormats.ScientificFormatLength.ScientificFormat scientificFormat;

                /**
                 * Obtiene el valor de la propiedad scientificFormat.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Ldml.Numbers.ScientificFormats.ScientificFormatLength.ScientificFormat }
                 *     
                 */
                public Ldml.Numbers.ScientificFormats.ScientificFormatLength.ScientificFormat getScientificFormat() {
                    return scientificFormat;
                }

                /**
                 * Define el valor de la propiedad scientificFormat.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Ldml.Numbers.ScientificFormats.ScientificFormatLength.ScientificFormat }
                 *     
                 */
                public void setScientificFormat(Ldml.Numbers.ScientificFormats.ScientificFormatLength.ScientificFormat value) {
                    this.scientificFormat = value;
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
                 *         &lt;element name="pattern" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
                    "pattern"
                })
                public static class ScientificFormat {

                    @XmlElement(required = true)
                    protected String pattern;

                    /**
                     * Obtiene el valor de la propiedad pattern.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getPattern() {
                        return pattern;
                    }

                    /**
                     * Define el valor de la propiedad pattern.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setPattern(String value) {
                        this.pattern = value;
                    }

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
         *         &lt;element name="decimal" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="group" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="list" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="percentSign" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="plusSign" type="{http://www.w3.org/2001/XMLSchema}byte"/>
         *         &lt;element name="minusSign" type="{http://www.w3.org/2001/XMLSchema}byte"/>
         *         &lt;element name="exponential" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="superscriptingExponent" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="perMille" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="infinity" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="nan" type="{http://www.w3.org/2001/XMLSchema}float"/>
         *         &lt;element name="timeSeparator">
         *           &lt;complexType>
         *             &lt;simpleContent>
         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                 &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
         *               &lt;/extension>
         *             &lt;/simpleContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *       &lt;attribute name="numberSystem" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "decimal",
            "group",
            "list",
            "percentSign",
            "plusSign",
            "minusSign",
            "exponential",
            "superscriptingExponent",
            "perMille",
            "infinity",
            "nan",
            "timeSeparator"
        })
        public static class Symbols {

            @XmlElement(required = true)
            protected String decimal;
            @XmlElement(required = true)
            protected String group;
            @XmlElement(required = true)
            protected String list;
            @XmlElement(required = true)
            protected String percentSign;
            protected byte plusSign;
            protected byte minusSign;
            @XmlElement(required = true)
            protected String exponential;
            @XmlElement(required = true)
            protected String superscriptingExponent;
            @XmlElement(required = true)
            protected String perMille;
            @XmlElement(required = true)
            protected String infinity;
            protected float nan;
            @XmlElement(required = true)
            protected Ldml.Numbers.Symbols.TimeSeparator timeSeparator;
            @XmlAttribute(name = "numberSystem")
            protected String numberSystem;

            /**
             * Obtiene el valor de la propiedad decimal.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDecimal() {
                return decimal;
            }

            /**
             * Define el valor de la propiedad decimal.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDecimal(String value) {
                this.decimal = value;
            }

            /**
             * Obtiene el valor de la propiedad group.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getGroup() {
                return group;
            }

            /**
             * Define el valor de la propiedad group.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setGroup(String value) {
                this.group = value;
            }

            /**
             * Obtiene el valor de la propiedad list.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getList() {
                return list;
            }

            /**
             * Define el valor de la propiedad list.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setList(String value) {
                this.list = value;
            }

            /**
             * Obtiene el valor de la propiedad percentSign.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPercentSign() {
                return percentSign;
            }

            /**
             * Define el valor de la propiedad percentSign.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPercentSign(String value) {
                this.percentSign = value;
            }

            /**
             * Obtiene el valor de la propiedad plusSign.
             * 
             */
            public byte getPlusSign() {
                return plusSign;
            }

            /**
             * Define el valor de la propiedad plusSign.
             * 
             */
            public void setPlusSign(byte value) {
                this.plusSign = value;
            }

            /**
             * Obtiene el valor de la propiedad minusSign.
             * 
             */
            public byte getMinusSign() {
                return minusSign;
            }

            /**
             * Define el valor de la propiedad minusSign.
             * 
             */
            public void setMinusSign(byte value) {
                this.minusSign = value;
            }

            /**
             * Obtiene el valor de la propiedad exponential.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getExponential() {
                return exponential;
            }

            /**
             * Define el valor de la propiedad exponential.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setExponential(String value) {
                this.exponential = value;
            }

            /**
             * Obtiene el valor de la propiedad superscriptingExponent.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSuperscriptingExponent() {
                return superscriptingExponent;
            }

            /**
             * Define el valor de la propiedad superscriptingExponent.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSuperscriptingExponent(String value) {
                this.superscriptingExponent = value;
            }

            /**
             * Obtiene el valor de la propiedad perMille.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPerMille() {
                return perMille;
            }

            /**
             * Define el valor de la propiedad perMille.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPerMille(String value) {
                this.perMille = value;
            }

            /**
             * Obtiene el valor de la propiedad infinity.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getInfinity() {
                return infinity;
            }

            /**
             * Define el valor de la propiedad infinity.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setInfinity(String value) {
                this.infinity = value;
            }

            /**
             * Obtiene el valor de la propiedad nan.
             * 
             */
            public float getNan() {
                return nan;
            }

            /**
             * Define el valor de la propiedad nan.
             * 
             */
            public void setNan(float value) {
                this.nan = value;
            }

            /**
             * Obtiene el valor de la propiedad timeSeparator.
             * 
             * @return
             *     possible object is
             *     {@link Ldml.Numbers.Symbols.TimeSeparator }
             *     
             */
            public Ldml.Numbers.Symbols.TimeSeparator getTimeSeparator() {
                return timeSeparator;
            }

            /**
             * Define el valor de la propiedad timeSeparator.
             * 
             * @param value
             *     allowed object is
             *     {@link Ldml.Numbers.Symbols.TimeSeparator }
             *     
             */
            public void setTimeSeparator(Ldml.Numbers.Symbols.TimeSeparator value) {
                this.timeSeparator = value;
            }

            /**
             * Obtiene el valor de la propiedad numberSystem.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNumberSystem() {
                return numberSystem;
            }

            /**
             * Define el valor de la propiedad numberSystem.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNumberSystem(String value) {
                this.numberSystem = value;
            }


            /**
             * <p>Clase Java para anonymous complex type.
             * 
             * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;simpleContent>
             *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *       &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/extension>
             *   &lt;/simpleContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "value"
            })
            public static class TimeSeparator {

                @XmlValue
                protected String value;
                @XmlAttribute(name = "draft")
                protected String draft;

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

                /**
                 * Obtiene el valor de la propiedad draft.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getDraft() {
                    return draft;
                }

                /**
                 * Define el valor de la propiedad draft.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setDraft(String value) {
                    this.draft = value;
                }

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
     *         &lt;element name="messages">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="yesstr" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="nostr" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "messages"
    })
    public static class Posix {

        @XmlElement(required = true)
        protected Ldml.Posix.Messages messages;

        /**
         * Obtiene el valor de la propiedad messages.
         * 
         * @return
         *     possible object is
         *     {@link Ldml.Posix.Messages }
         *     
         */
        public Ldml.Posix.Messages getMessages() {
            return messages;
        }

        /**
         * Define el valor de la propiedad messages.
         * 
         * @param value
         *     allowed object is
         *     {@link Ldml.Posix.Messages }
         *     
         */
        public void setMessages(Ldml.Posix.Messages value) {
            this.messages = value;
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
         *         &lt;element name="yesstr" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="nostr" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
            "yesstr",
            "nostr"
        })
        public static class Messages {

            @XmlElement(required = true)
            protected String yesstr;
            @XmlElement(required = true)
            protected String nostr;

            /**
             * Obtiene el valor de la propiedad yesstr.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getYesstr() {
                return yesstr;
            }

            /**
             * Define el valor de la propiedad yesstr.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setYesstr(String value) {
                this.yesstr = value;
            }

            /**
             * Obtiene el valor de la propiedad nostr.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNostr() {
                return nostr;
            }

            /**
             * Define el valor de la propiedad nostr.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNostr(String value) {
                this.nostr = value;
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
     *         &lt;element name="unitLength" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="compoundUnit">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="compoundUnitPattern">
     *                               &lt;complexType>
     *                                 &lt;simpleContent>
     *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                                     &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                   &lt;/extension>
     *                                 &lt;/simpleContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="unit" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="displayName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                             &lt;element name="unitPattern" maxOccurs="unbounded" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;simpleContent>
     *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                                     &lt;attribute name="count" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                     &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                   &lt;/extension>
     *                                 &lt;/simpleContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                             &lt;element name="perUnitPattern" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                           &lt;/sequence>
     *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="durationUnit" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="durationUnitPattern" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
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
        "unitLength",
        "durationUnit"
    })
    public static class Units {

        protected List<Ldml.Units.UnitLength> unitLength;
        protected List<Ldml.Units.DurationUnit> durationUnit;

        /**
         * Gets the value of the unitLength property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the unitLength property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getUnitLength().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Ldml.Units.UnitLength }
         * 
         * 
         */
        public List<Ldml.Units.UnitLength> getUnitLength() {
            if (unitLength == null) {
                unitLength = new ArrayList<Ldml.Units.UnitLength>();
            }
            return this.unitLength;
        }

        /**
         * Gets the value of the durationUnit property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the durationUnit property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDurationUnit().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Ldml.Units.DurationUnit }
         * 
         * 
         */
        public List<Ldml.Units.DurationUnit> getDurationUnit() {
            if (durationUnit == null) {
                durationUnit = new ArrayList<Ldml.Units.DurationUnit>();
            }
            return this.durationUnit;
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
         *         &lt;element name="durationUnitPattern" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *       &lt;/sequence>
         *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "durationUnitPattern"
        })
        public static class DurationUnit {

            @XmlElement(required = true)
            protected String durationUnitPattern;
            @XmlAttribute(name = "type")
            protected String type;

            /**
             * Obtiene el valor de la propiedad durationUnitPattern.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDurationUnitPattern() {
                return durationUnitPattern;
            }

            /**
             * Define el valor de la propiedad durationUnitPattern.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDurationUnitPattern(String value) {
                this.durationUnitPattern = value;
            }

            /**
             * Obtiene el valor de la propiedad type.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getType() {
                return type;
            }

            /**
             * Define el valor de la propiedad type.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setType(String value) {
                this.type = value;
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
         *         &lt;element name="compoundUnit">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="compoundUnitPattern">
         *                     &lt;complexType>
         *                       &lt;simpleContent>
         *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                           &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                         &lt;/extension>
         *                       &lt;/simpleContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="unit" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="displayName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;element name="unitPattern" maxOccurs="unbounded" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;simpleContent>
         *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                           &lt;attribute name="count" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                           &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                         &lt;/extension>
         *                       &lt;/simpleContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                   &lt;element name="perUnitPattern" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                 &lt;/sequence>
         *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "compoundUnit",
            "unit"
        })
        public static class UnitLength {

            @XmlElement(required = true)
            protected Ldml.Units.UnitLength.CompoundUnit compoundUnit;
            protected List<Ldml.Units.UnitLength.Unit> unit;
            @XmlAttribute(name = "type")
            protected String type;

            /**
             * Obtiene el valor de la propiedad compoundUnit.
             * 
             * @return
             *     possible object is
             *     {@link Ldml.Units.UnitLength.CompoundUnit }
             *     
             */
            public Ldml.Units.UnitLength.CompoundUnit getCompoundUnit() {
                return compoundUnit;
            }

            /**
             * Define el valor de la propiedad compoundUnit.
             * 
             * @param value
             *     allowed object is
             *     {@link Ldml.Units.UnitLength.CompoundUnit }
             *     
             */
            public void setCompoundUnit(Ldml.Units.UnitLength.CompoundUnit value) {
                this.compoundUnit = value;
            }

            /**
             * Gets the value of the unit property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the unit property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getUnit().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Ldml.Units.UnitLength.Unit }
             * 
             * 
             */
            public List<Ldml.Units.UnitLength.Unit> getUnit() {
                if (unit == null) {
                    unit = new ArrayList<Ldml.Units.UnitLength.Unit>();
                }
                return this.unit;
            }

            /**
             * Obtiene el valor de la propiedad type.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getType() {
                return type;
            }

            /**
             * Define el valor de la propiedad type.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setType(String value) {
                this.type = value;
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
             *         &lt;element name="compoundUnitPattern">
             *           &lt;complexType>
             *             &lt;simpleContent>
             *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *                 &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
             *               &lt;/extension>
             *             &lt;/simpleContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *       &lt;/sequence>
             *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "compoundUnitPattern"
            })
            public static class CompoundUnit {

                @XmlElement(required = true)
                protected Ldml.Units.UnitLength.CompoundUnit.CompoundUnitPattern compoundUnitPattern;
                @XmlAttribute(name = "type")
                protected String type;

                /**
                 * Obtiene el valor de la propiedad compoundUnitPattern.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Ldml.Units.UnitLength.CompoundUnit.CompoundUnitPattern }
                 *     
                 */
                public Ldml.Units.UnitLength.CompoundUnit.CompoundUnitPattern getCompoundUnitPattern() {
                    return compoundUnitPattern;
                }

                /**
                 * Define el valor de la propiedad compoundUnitPattern.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Ldml.Units.UnitLength.CompoundUnit.CompoundUnitPattern }
                 *     
                 */
                public void setCompoundUnitPattern(Ldml.Units.UnitLength.CompoundUnit.CompoundUnitPattern value) {
                    this.compoundUnitPattern = value;
                }

                /**
                 * Obtiene el valor de la propiedad type.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getType() {
                    return type;
                }

                /**
                 * Define el valor de la propiedad type.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setType(String value) {
                    this.type = value;
                }


                /**
                 * <p>Clase Java para anonymous complex type.
                 * 
                 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;simpleContent>
                 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                 *       &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
                 *     &lt;/extension>
                 *   &lt;/simpleContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "value"
                })
                public static class CompoundUnitPattern {

                    @XmlValue
                    protected String value;
                    @XmlAttribute(name = "draft")
                    protected String draft;

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

                    /**
                     * Obtiene el valor de la propiedad draft.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getDraft() {
                        return draft;
                    }

                    /**
                     * Define el valor de la propiedad draft.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setDraft(String value) {
                        this.draft = value;
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
             *         &lt;element name="displayName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *         &lt;element name="unitPattern" maxOccurs="unbounded" minOccurs="0">
             *           &lt;complexType>
             *             &lt;simpleContent>
             *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *                 &lt;attribute name="count" type="{http://www.w3.org/2001/XMLSchema}string" />
             *                 &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
             *               &lt;/extension>
             *             &lt;/simpleContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="perUnitPattern" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
             *       &lt;/sequence>
             *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "displayName",
                "unitPattern",
                "perUnitPattern"
            })
            public static class Unit {

                protected String displayName;
                protected List<Ldml.Units.UnitLength.Unit.UnitPattern> unitPattern;
                protected String perUnitPattern;
                @XmlAttribute(name = "type")
                protected String type;

                /**
                 * Obtiene el valor de la propiedad displayName.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getDisplayName() {
                    return displayName;
                }

                /**
                 * Define el valor de la propiedad displayName.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setDisplayName(String value) {
                    this.displayName = value;
                }

                /**
                 * Gets the value of the unitPattern property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the unitPattern property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getUnitPattern().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link Ldml.Units.UnitLength.Unit.UnitPattern }
                 * 
                 * 
                 */
                public List<Ldml.Units.UnitLength.Unit.UnitPattern> getUnitPattern() {
                    if (unitPattern == null) {
                        unitPattern = new ArrayList<Ldml.Units.UnitLength.Unit.UnitPattern>();
                    }
                    return this.unitPattern;
                }

                /**
                 * Obtiene el valor de la propiedad perUnitPattern.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getPerUnitPattern() {
                    return perUnitPattern;
                }

                /**
                 * Define el valor de la propiedad perUnitPattern.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setPerUnitPattern(String value) {
                    this.perUnitPattern = value;
                }

                /**
                 * Obtiene el valor de la propiedad type.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getType() {
                    return type;
                }

                /**
                 * Define el valor de la propiedad type.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setType(String value) {
                    this.type = value;
                }


                /**
                 * <p>Clase Java para anonymous complex type.
                 * 
                 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;simpleContent>
                 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
                 *       &lt;attribute name="count" type="{http://www.w3.org/2001/XMLSchema}string" />
                 *       &lt;attribute name="draft" type="{http://www.w3.org/2001/XMLSchema}string" />
                 *     &lt;/extension>
                 *   &lt;/simpleContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "value"
                })
                public static class UnitPattern {

                    @XmlValue
                    protected String value;
                    @XmlAttribute(name = "count")
                    protected String count;
                    @XmlAttribute(name = "draft")
                    protected String draft;

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

                    /**
                     * Obtiene el valor de la propiedad count.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getCount() {
                        return count;
                    }

                    /**
                     * Define el valor de la propiedad count.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setCount(String value) {
                        this.count = value;
                    }

                    /**
                     * Obtiene el valor de la propiedad draft.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getDraft() {
                        return draft;
                    }

                    /**
                     * Define el valor de la propiedad draft.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setDraft(String value) {
                        this.draft = value;
                    }

                }

            }

        }

    }

}
