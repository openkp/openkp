package pl.openkp.business.uzytkownicy.boundary;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Scanner;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import pl.openkp.business.uzytkownicy.entity.Uzytkownik;

@WebServlet(name = "upload", urlPatterns = "/upload")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, maxFileSize = 1024 * 1024 * 50, maxRequestSize = 1024 * 1024 * 100)
public class ProfilServlet extends HttpServlet {
    private static final int BUF_SIZE = 0x1000; // 4K

    private static final long serialVersionUID = -8244073279641189889L;

    @Inject
    UzytkownikResource uzytkownikResource;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Uzytkownik encja = uzytkownik(req.getPart("uzytkownik"));
        encja.setZdjecie(zdjecie(req.getPart("zdjecie")));
        uzytkownikResource.zapisz(encja);
    }

    private byte[] zdjecie(Part part) {
        byte[] wynik = null;
        if (part != null && part.getSubmittedFileName() != null) {
            try (InputStream in = part.getInputStream()) {
                wynik = toByteArray(in);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        return wynik;
    }

    public static byte[] toByteArray(InputStream from) throws IOException {
        ByteArrayOutputStream to = new ByteArrayOutputStream();
        byte[] buf = new byte[BUF_SIZE];
        while (true) {
            int r = from.read(buf);
            if (r == -1) {
                break;
            }
            to.write(buf, 0, r);
        }
        return to.toByteArray();
    }

    private Uzytkownik uzytkownik(Part part) {
        Uzytkownik wynik = null;
        if (part != null) {
            try (InputStream stream = part.getInputStream();
                    Scanner s = new Scanner(stream);
                    Scanner scanner = s.useDelimiter("\\A");
                    JsonReader reader = Json.createReader(new StringReader(scanner.hasNext() ? scanner.next() : ""));) {

                JsonObject json = reader.readObject();
                wynik = new Uzytkownik();
                wynik.setId(json.containsKey("id") && !json.getString("id").isEmpty() ? json.getJsonNumber("id").longValue() : null);
                wynik.setImie(json.containsKey("imie") ? json.getString("imie") : null);
                wynik.setNazwisko(json.containsKey("nazwisko") ? json.getString("nazwisko") : null);
                wynik.setEmail(json.containsKey("email") ? json.getString("email") : null);
                wynik.setHaslo(json.containsKey("haslo") ? json.getString("haslo").getBytes() : null);
                wynik.setLogin(json.containsKey("login") ? json.getString("login") : null);
                wynik.setVersion(json.containsKey("version") && !json.getString("version").trim().isEmpty() ? json.getJsonNumber("version")
                        .intValue() : 0);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return wynik;
    }
}