package es.mcg;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class WriterXmlHomes {
    private static Casa createNewHome()
    {
        return new Casa("Jaen", "adosado", 300, 2, 7, false);
    }
    private static void writerXml(Document document, OutputStream outputStream)
    {
        try
        {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource xmlAsObject = new DOMSource(document);
            StreamResult xmlAsFile = new StreamResult(outputStream);
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}-indent-amount", "2");
            transformer.transform(xmlAsObject, xmlAsFile);
        }
        catch(TransformerException transformerException)
        {
            transformerException.printStackTrace();
        }
    }
    public static class Casa {
        private String ciudad, tipo;
        private Integer metrosCuadrados, plantas, habitaciones;
        private Boolean zonasComunes;
        public Casa(String ciudad, String tipo, Integer metrosCuadrados, Integer plantas, Integer habitaciones,
                Boolean zonasComunes) {
            this.ciudad = ciudad;
            this.tipo = tipo;
            this.metrosCuadrados = metrosCuadrados;
            this.plantas = plantas;
            this.habitaciones = habitaciones;
            this.zonasComunes = zonasComunes;
        }
        public String getCiudad() {
            return ciudad;
        }
        public void setCiudad(String ciudad) {
            this.ciudad = ciudad;
        }
        public String getTipo() {
            return tipo;
        }
        public void setTipo(String tipo) {
            this.tipo = tipo;
        }
        public Integer getMetrosCuadrados() {
            return metrosCuadrados;
        }
        public void setMetrosCuadrados(Integer metrosCuadrados) {
            this.metrosCuadrados = metrosCuadrados;
        }
        public Integer getPlantas() {
            return plantas;
        }
        public void setPlantas(Integer plantas) {
            this.plantas = plantas;
        }
        public Integer getHabitaciones() {
            return habitaciones;
        }
        public void setHabitaciones(Integer habitaciones) {
            this.habitaciones = habitaciones;
        }
        public Boolean getZonasComunes() {
            return zonasComunes;
        }
        public void setZonasComunes(Boolean zonasComunes) {
            this.zonasComunes = zonasComunes;
        }
        @Override
        public String toString() {
            return "Casa [ciudad=" + ciudad + ", tipo=" + tipo + ", metrosCuadrados=" + metrosCuadrados + ", plantas="
                    + plantas + ", habitaciones=" + habitaciones + ", zonasComunes=" + zonasComunes + "]";
        } 
    }
    public static void main(String[] args) {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        Casa casa = createNewHome();
        try 
        {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            FileOutputStream outputStream = new FileOutputStream("homes-out.xml");
            Document document = documentBuilder.newDocument();
            Element homesElement = document.createElement("casas");
            Element homeElement = document.createElement("casa");
            Attr cityAttr = document.createAttribute("ciudad");
            cityAttr.setValue(casa.getCiudad());
            Attr typeAttr = document.createAttribute("tipo");
            typeAttr.setValue(casa.getTipo());
            Attr metersAttr = document.createAttribute("metrosCuadrados");
            metersAttr.setValue(String.valueOf(casa.getMetrosCuadrados()));
            Attr floorsAttr = document.createAttribute("plantas");
            floorsAttr.setValue(String.valueOf(casa.getPlantas()));
            Attr roomsAttr = document.createAttribute("habitaciones");
            roomsAttr.setValue(String.valueOf(casa.getHabitaciones()));
            Attr commonAttr = document.createAttribute("zonasComunes");
            commonAttr.setValue(String.valueOf(casa.getZonasComunes()));
            homeElement.getAttributes().setNamedItem(cityAttr);
            homeElement.getAttributes().setNamedItem(typeAttr);
            homeElement.getAttributes().setNamedItem(metersAttr);
            homeElement.getAttributes().setNamedItem(floorsAttr);
            homeElement.getAttributes().setNamedItem(roomsAttr);
            homeElement.getAttributes().setNamedItem(commonAttr);
            homesElement.appendChild(homeElement);
            document.appendChild(homesElement);
            writerXml(document, outputStream);
        } 
        catch (ParserConfigurationException | IOException xmlException) 
        {
            xmlException.printStackTrace();
        }
    }
}
