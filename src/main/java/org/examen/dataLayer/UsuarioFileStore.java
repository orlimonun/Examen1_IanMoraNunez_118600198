package org.examen.dataLayer;
import jakarta.xml.bind.JAXBContext;

import org.examen.domainLayer.Usuario;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import javax.xml.stream.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class UsuarioFileStore implements IFileStore<Usuario> {
    private final File xmlFile;

    public UsuarioFileStore(File xmlFile) {
        this.xmlFile = xmlFile;
        ensureFile();
    }
    private void ensureFile() {
        try {
            File parent = xmlFile.getParentFile();

            if (parent != null) {
                parent.mkdirs();
            }

            if (!xmlFile.exists()) {
                xmlFile.createNewFile();
                writeAll(new ArrayList<>());
            }
        } catch (Exception ignored) {}
    }
    @Override
    public void writeAll(List<Usuario> data) {
        try (FileOutputStream out = new FileOutputStream(xmlFile)) {
            JAXBContext ctx = JAXBContext.newInstance(Usuario.class);
            Marshaller m = ctx.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.setProperty(Marshaller.JAXB_FRAGMENT, true);

            XMLOutputFactory xof = XMLOutputFactory.newFactory();
            XMLStreamWriter xw = xof.createXMLStreamWriter(out, "UTF-8");

            xw.writeStartDocument("UTF-8", "1.0");
            xw.writeStartElement("usuarios");

            if (data != null) {
                for (Usuario u : data) {
                    m.marshal(u, xw);
                }
            }

            xw.writeEndElement();
            xw.writeEndDocument();
            xw.flush();
            xw.close();
        } catch (Exception ex) {
            System.err.println("[WARN] Error escribiendo " + xmlFile);
            ex.printStackTrace();
        }
    }
    @Override
    public List<Usuario> readAll() {
        List<Usuario> out = new ArrayList<>();
        if (xmlFile.length() == 0) return out;

        try {
            JAXBContext ctx = JAXBContext.newInstance(Usuario.class);
            Unmarshaller u = ctx.createUnmarshaller();


            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xmlFile);
            System.out.println("tamaño del xml"+ xmlFile.length());



            NodeList usuarioNodes = doc.getElementsByTagName("usuario");

            for (int i = 0; i < usuarioNodes.getLength(); i++) {
                Node usuarioNode = usuarioNodes.item(i);
                if (usuarioNode.getNodeType() == Node.ELEMENT_NODE){
                    try {
                        Usuario pro = (Usuario) u.unmarshal(usuarioNode);
                        out.add(pro);
                    } catch (Exception e) {
                        System.err.println("[WARN] Error deserializando usuario " + i + ": " + e.getMessage());
                    }
                }
            }

        } catch (Exception ex) {
            System.err.println("[WARN] Error leyendo " + xmlFile + ": " + ex.getMessage());
            ex.printStackTrace();
        }
        return out;
    }
}
