package tool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;


 
 
/**
 * YAMLGenerator. This is simple tool class to generate a template YAML file based on old java bean classes.
 * The output result contains API operation of Entity(Post/get/put/delete) RESTful API, You can change it according the requirement.
 * The Entities maybe not really suitable for the requirement, you can change anything according the SWAGGER document http://docs.swagger.io/spec.html
 * @author <A HREF="mailto:yaming.cheng@neulion.cn">Yaming Cheng</A>
 * @version 1.0, $Revision: 0$
 * @date 2017-06-16 16:33
 * @since 1.0
 * 
 * 
 * The sample like following:
 * 
 * YAMLGenerator ./game/game.yaml E:\cmsv7\ws\iptv-content\src\com\neulion\iptv\content\game\Game.java E:\cmsv7\ws\iptv-content\src\com\neulion\iptv\content\game\GameAddInfo.java E:\cmsv7\ws\iptv-content\src\com\neulion\iptv\content\game\GameProgram.java E:\cmsv7\ws\iptv-content\src\com\neulion\iptv\content\game\GameType.java E:\cmsv7\ws\iptv-content\src\com\neulion\iptv\content\game\GameTypeID.java E:\cmsv7\ws\iptv-platform\src\com\neulion\iptv\content\game\GameProgramType.java
 */
public class YAMLGenerator
{

    public static void main(String[] args)
    {
        if (args.length == 0)
        {
            System.out.print(
                    "the parameters is configuration.json+files of java beans");
            return;
        }
        String[] files = new String[args.length - 1];
        for (int i = 1; i < args.length; i++)
            files[i - 1] = args[i];
        createAPI(args[0], files);
    }

    private static List<String> lines = new ArrayList<String>();

    private static void createAPI(String yaml, String[] files)
    {

        List<String> fileNames = new ArrayList<String>();
        for (String f : files)
        {
            String name = new File(f).getName();
            fileNames.add(name.substring(0, name.indexOf('.')));
        }
        lines.add(
                "# This is a sample Swagger spec, describing a simple API as a starting point.  ");
        lines.add("swagger: \"2.0\"");
        lines.add("info:");
        lines.add("  description: Game entity for game business logic");
        lines.add("  version: 1.0.0");
        lines.add("  title: xxxxx");
        lines.add("host: neulion.com");
        lines.add("basePath: /cms/v1");
        lines.add("schemes:");
        lines.add("- http");
        lines.add("tags:");
        lines.add("  - name: xxxx");
        lines.add("    description: xxxxxxxxxxxxxx.");
        lines.add("paths:");
        for (String path : fileNames)
        {
            lines.add("#############################################");
            lines.add("  /" + path.toLowerCase() + ":");
            lines.add("    " + "post:");
            lines.add("      tags:");
            lines.add("        - " + path);
            lines.add("      summary: create " + path);
            lines.add("      description: Create object with full info.");
            lines.add("      operationId: create" + path);
            lines.add("      produces:");
            lines.add("        - application/xml");
            lines.add("        - application/json");
            lines.add("      parameters:");
            lines.add("        - in: body");
            lines.add("          name: body");
            lines.add("          description: Create entity");
            lines.add("          required: true");
            lines.add("          schema:");
            lines.add("            $ref: '#/definitions/" + path + "'");
            lines.add("      responses:");
            lines.add("        default:");
            lines.add("          description: successful operation");
            lines.add("        '405':");
            lines.add("          description: Validation exception");

            lines.add("    " + "get:");
            lines.add("      tags:");
            lines.add("        - " + path);
            lines.add("      summary: query " + path);
            lines.add(
                    "      description: Query objects according given conditions.");
            lines.add("      operationId: query" + path);
            lines.add("      produces:");
            lines.add("        - application/xml");
            lines.add("        - application/json");
            lines.add("      parameters:");
            lines.add("        - in: query");
            lines.add("          name: xxx");
            lines.add("          type: string");
            lines.add("          description: xxx");
            lines.add("          required: false");
            lines.add("        - in: query");
            lines.add("          name: xxx");
            lines.add("          type: integer");
            lines.add("          description: xxx");
            lines.add("          required: true");
            lines.add("          format: int32");
            lines.add("        - name: start");
            lines.add(
                    "          description: the first record to be returned.");
            lines.add("          in: query");
            lines.add("          required: false");
            lines.add("          type: integer");
            lines.add("          format: int32");
            lines.add("        - name: max");
            lines.add(
                    "          description: the max number of records to be returned.");
            lines.add("          in: query");
            lines.add("          required: false");
            lines.add("          type: integer");
            lines.add("          format: int32");
            lines.add("      responses:");
            lines.add("        '200':");
            lines.add("          description: successful operation");
            lines.add("          schema:");
            lines.add("            type: array");
            lines.add("            items:");
            lines.add("              $ref: '#/definitions/" + path + "'");
            lines.add("        '404':");
            lines.add(
                    "          description: The requested game was not found");

            lines.add("  /" + path.toLowerCase() + "/{id}:");
            lines.add("    get:");
            lines.add("      tags:");
            lines.add("        - " + path);
            lines.add("      summary: get a single " + path);
            lines.add("      description: Get the object with its ID.");
            lines.add("      operationId: get" + path);
            lines.add("      produces:");
            lines.add("        - application/xml");
            lines.add("        - application/json");
            lines.add("      parameters:");
            lines.add("        - in: path");
            lines.add("          name: id");
            lines.add("          type: integer");
            lines.add("          format: int32");
            lines.add("          required: true");
            lines.add("      responses:");
            lines.add("        '200':");
            lines.add("          description: successful operation");
            lines.add("          schema:");
            lines.add("            $ref: '#/definitions/" + path + "'");
            lines.add("        '404':");
            lines.add(
                    "          description: The requested game was not found");

            lines.add("    put:");
            lines.add("      tags:");
            lines.add("        - " + path);
            lines.add("      summary: update " + path);
            lines.add("      description: Update object with full info.");
            lines.add("      operationId: update" + path);
            lines.add("      consumes:");
            lines.add("        - application/x-www-form-urlencoded");
            lines.add("      produces:");
            lines.add("        - application/xml");
            lines.add("        - application/json");
            lines.add("      parameters:");
            lines.add("        - in: path");
            lines.add("          name: id");
            lines.add("          type: integer");
            lines.add("          format: int32");
            lines.add("          required: true");
            lines.add("        - in: body");
            lines.add("          name: body");
            lines.add("          required: true");
            lines.add("          schema:");
            lines.add("            $ref: '#/definitions/" + path + "'");
            lines.add("      responses:");
            lines.add("        default:");
            lines.add("          description: successful operation");
            lines.add("        '404':");
            lines.add("          description: Object not found");
            lines.add("        '405':");
            lines.add("          description: Validation exception");

            lines.add("    delete:");
            lines.add("      tags:");
            lines.add("        - " + path);
            lines.add("      summary: delete " + path);
            lines.add("      description: delete object with specified ID.");
            lines.add("      operationId: delete" + path);
            lines.add("      produces:");
            lines.add("        - application/xml");
            lines.add("        - application/json");
            lines.add("      parameters:");
            lines.add("        - in: path");
            lines.add("          name: id");
            lines.add("          type: integer");
            lines.add("          format: int32");
            lines.add("          required: true");
            lines.add("      responses:");
            lines.add("        default:");
            lines.add("          description: successful operation");
            lines.add("        '404':");
            lines.add("          description: Object not found");
            lines.add("        '405':");
            lines.add("          description: Validation exception");

        }
        lines.add("definitions:");
        File fout = new File(yaml);
        try
        {
            FileOutputStream fos = new FileOutputStream(fout);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

            for (String line : lines)
            {
                bw.write(line);
                bw.newLine();
            }
            for (int i = 0; i < files.length; i++)
            {
                String entityName = fileNames.get(i);
                bw.write("  " + entityName + ":");
                bw.newLine();
                bw.write("    type: object");
                bw.newLine();
                bw.write("    description: xxxxxxxx.");
                bw.newLine();
                bw.write("    required:");
                bw.newLine();
                bw.write("      - id");
                bw.newLine();
                bw.write(addObjectDefination(files[i]));
                bw.newLine();
            }
            bw.close();
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private static String addObjectDefination(String fileName)
    {

        StringBuilder output = new StringBuilder("    properties:");

        StringBuilder example = new StringBuilder();
        Stream<String> stream = null;
        String sep = System.getProperty("line.separator");
        try
        {
            stream = Files.lines(Paths.get(fileName));
            Iterator<String> result = stream.iterator();
            while (result.hasNext())
            {

                String line = result.next().trim();
                if (line.length() == 0 || !line.endsWith(";"))
                {
                    continue;
                }

                if (line.startsWith("private String "))
                {
                    output.append(sep);
                    output.append("      ");
                    output.append(line.substring(15, line.indexOf(";")));
                    output.append(":");
                    output.append(sep);
                    output.append("        ");
                    output.append("type: string");

                    example.append(sep);
                    example.append(line.substring(15, line.indexOf(";")));
                    example.append(" : sample value,");
                }
                else if (line.startsWith("private Integer "))
                {
                    output.append(sep);
                    output.append("      ");
                    output.append(line.substring(16, line.indexOf(";")));
                    output.append(":");
                    output.append(sep);
                    output.append("        ");
                    output.append("type: integer");
                    output.append(sep);
                    output.append("        ");
                    output.append("format: int32");

                    example.append(sep);
                    example.append(line.substring(16, line.indexOf(";")));
                    example.append(" : 11,");
                }
                else if (line.startsWith("private Boolean "))
                {
                    output.append(sep);
                    output.append("      ");
                    output.append(line.substring(16, line.indexOf(";")));
                    output.append(":");
                    output.append(sep);
                    output.append("        ");
                    output.append("type: boolean");

                    example.append(sep);
                    example.append(line.substring(16, line.indexOf(";")));
                    example.append(" : true,");
                }
                else if (line.startsWith("private int "))
                {
                    output.append(sep);
                    output.append("      ");
                    output.append(line.substring(12, line.indexOf(";")));
                    output.append(":");
                    output.append(sep);
                    output.append("        ");
                    output.append("type: integer");
                    output.append(sep);
                    output.append("        ");
                    output.append("format: int32");

                    example.append(sep);
                    example.append(line.substring(12, line.indexOf(";")));
                    example.append(" : 1,");
                }
                else if (line.startsWith("private Long "))
                {
                    output.append(sep);
                    output.append("      ");
                    output.append(line.substring(13, line.indexOf(";")));
                    output.append(":");
                    output.append(sep);
                    output.append("        ");
                    output.append("type: integer");
                    output.append(sep);
                    output.append("        ");
                    output.append("format: int64");

                    example.append(sep);
                    example.append(line.substring(13, line.indexOf(";")));
                    example.append(" : 1000l,");
                }
                else if (line.startsWith("private Date "))
                {
                    output.append(sep);
                    output.append("      ");
                    output.append(line.substring(13, line.indexOf(";")));
                    output.append(":");
                    output.append(sep);
                    output.append("        ");
                    output.append("type: string");
                    output.append(sep);
                    output.append("        ");
                    output.append("format: date");

                    example.append(sep);
                    example.append(line.substring(13, line.indexOf(";")));
                    example.append(" : 201701040828,");
                }
                else if (line.startsWith("private Float "))
                {
                    output.append(sep);
                    output.append("      ");
                    output.append(line.substring(14, line.indexOf(";")));
                    output.append(":");
                    output.append(sep);
                    output.append("        ");
                    output.append("type: number");
                    output.append(sep);
                    output.append("        ");
                    output.append("format: float");

                    example.append(sep);
                    example.append(line.substring(14, line.indexOf(";")));
                    example.append(" : 3.14,");
                }
                else if (line.startsWith("private Double "))
                {
                    output.append(sep);
                    output.append("      ");
                    output.append(line.substring(15, line.indexOf(";")));
                    output.append(":");
                    output.append(sep);
                    output.append("        ");
                    output.append("type: number");
                    output.append(sep);
                    output.append("        ");
                    output.append("format: double");

                    example.append(sep);
                    example.append(line.substring(15, line.indexOf(";")));
                    example.append(" : 0.314,");
                }
                else if (line.startsWith("private byte "))
                {
                    output.append(sep);
                    output.append("      ");
                    output.append(line.substring(13, line.indexOf(";")));
                    output.append(":");
                    output.append(sep);
                    output.append("        ");
                    output.append("type: string");
                    output.append(sep);
                    output.append("        ");
                    output.append("format: byte");

                    example.append(sep);
                    example.append(line.substring(13, line.indexOf(";")));
                    example.append(" : 1,");
                }
                else if (line.startsWith("private short "))
                {
                    output.append(sep);
                    output.append("      ");
                    output.append(line.substring(14, line.indexOf(";")));
                    output.append(":");
                    output.append(sep);
                    output.append("        ");
                    output.append("type: integer");
                    output.append(sep);
                    output.append("        ");
                    output.append("format: int32");

                    example.append(sep);
                    example.append(line.substring(13, line.indexOf(";")));
                    example.append(" : 1,");
                }
                else if (line.startsWith("private ") && line.endsWith(";")
                        && line.split(" ").length == 3)
                {
                    line = line.substring(8, line.length() - 1).trim();
                    String type = line.substring(0, line.indexOf(' '));
                    String value = line.substring(line.indexOf(' ')).trim();
                    output.append(sep);
                    output.append("      ");
                    output.append(value);
                    output.append(":");
                    output.append(sep);
                    output.append("        ");
                    output.append("$ref: '#/definitions/");
                    output.append(type);
                    output.append(
                            "'  #maybe another file./xxx.yaml/#definitions/"
                                    + type);
                }

            }
            System.out.println(example.toString());
            return output.toString();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally
        {
            if (stream != null)
                stream.close();
        }
        return "";

    }
}
