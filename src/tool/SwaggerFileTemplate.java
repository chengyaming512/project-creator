package tool;

/**
 * SwaggerFileTemplate. A simple tool for printing the API part based on Swagger Spec
 * @author <A HREF="mailto:yaming.cheng@neulion.cn">Yaming Cheng</A>
 * @version 1.0, $Revision: 0$
 * @date 2017-06-16 16:50
 * @since 1.0
 */
public class SwaggerFileTemplate
{

    public static void main(String[] args)
    {
        System.out.println(
                "# This is a sample Swagger spec, describing a simple API as a starting point.  ");
        System.out.println("swagger: \"2.0\"");
        System.out.println("info:");
        System.out
                .println("  description: Game entity for game business logic");
        System.out.println("  version: 1.0.0");
        System.out.println("  title: xxxxx");
        System.out.println("host: neulion.com");
        System.out.println("basePath: /cms/v1");
        System.out.println("schemes:");
        System.out.println("- http");
        System.out.println("tags:");
        System.out.println("  - name: xxxx");
        System.out.println("    description: xxxxxxxxxxxxxx.");
        System.out.println("paths:");
        for (String path : args)
        {
            System.out.println("#############################################");
            System.out.println("  /" + path.toLowerCase() + ":");
            System.out.println("    " + "post:");
            System.out.println("      tags:");
            System.out.println("        - " + path);
            System.out.println("      summary: create " + path);
            System.out.println(
                    "      description: Create object with full info.");
            System.out.println("      operationId: create" + path);
            System.out.println("      produces:");
            System.out.println("        - application/xml");
            System.out.println("        - application/json");
            System.out.println("      parameters:");
            System.out.println("        - in: body");
            System.out.println("          name: body");
            System.out.println("          description: Create entity");
            System.out.println("          required: true");
            System.out.println("          schema:");
            System.out
                    .println("            $ref: '#/definitions/" + path + "'");
            System.out.println("      responses:");
            System.out.println("        default:");
            System.out.println("          description: successful operation");
            System.out.println("        '405':");
            System.out.println("          description: Validation exception");

            System.out.println("    " + "get:");
            System.out.println("      tags:");
            System.out.println("        - XXX");
            System.out.println("      summary: query " + path);
            System.out.println(
                    "      description: Query objects according given conditions.");
            System.out.println("      operationId: query" + path);
            System.out.println("      produces:");
            System.out.println("        - application/xml");
            System.out.println("        - application/json");
            System.out.println("      parameters:");
            System.out.println("        - in: query");
            System.out.println("          name: xxx");
            System.out.println("          type: string");
            System.out.println("          description: xxx");
            System.out.println("          required: false");
            System.out.println("        - in: query");
            System.out.println("          name: xxx");
            System.out.println("          type: integer");
            System.out.println("          description: xxx");
            System.out.println("          required: true");
            System.out.println("          format: int32");
            System.out.println("        - name: start");
            System.out.println(
                    "          description: the first record to be returned.");
            System.out.println("          in: query");
            System.out.println("          required: false");
            System.out.println("          type: integer");
            System.out.println("          format: int32");
            System.out.println("        - name: max");
            System.out.println(
                    "          description: the max number of records to be returned.");
            System.out.println("          in: query");
            System.out.println("          required: false");
            System.out.println("          type: integer");
            System.out.println("          format: int32");
            System.out.println("      responses:");
            System.out.println("        '200':");
            System.out.println("          description: successful operation");
            System.out.println("          schema:");
            System.out.println("            type: array");
            System.out.println("            items:");
            System.out.println(
                    "              $ref: '#/definitions/" + path + "'");
            System.out.println("        '404':");
            System.out.println(
                    "          description: The requested game was not found");

            System.out.println("  /" + path.toLowerCase() + "/{id}:");
            System.out.println("    get:");
            System.out.println("      tags:");
            System.out.println("        - " + path);
            System.out.println("      summary: get a single " + path);
            System.out
                    .println("      description: Get the object with its ID.");
            System.out.println("      operationId: get" + path);
            System.out.println("      produces:");
            System.out.println("        - application/xml");
            System.out.println("        - application/json");
            System.out.println("      parameters:");
            System.out.println("        - in: path");
            System.out.println("          name: id");
            System.out.println("          type: integer");
            System.out.println("          format: int32");
            System.out.println("          required: true");
            System.out.println("      responses:");
            System.out.println("        '200':");
            System.out.println("          description: successful operation");
            System.out.println("          schema:");
            System.out
                    .println("            $ref: '#/definitions/" + path + "'");
            System.out.println("        '404':");
            System.out.println(
                    "          description: The requested game was not found");

            System.out.println("    put:");
            System.out.println("      tags:");
            System.out.println("        - " + path);
            System.out.println("      summary: update " + path);
            System.out.println(
                    "      description: Update object with full info.");
            System.out.println("      operationId: update" + path);
            System.out.println("      consumes:");
            System.out.println("        - application/x-www-form-urlencoded");
            System.out.println("      produces:");
            System.out.println("        - application/xml");
            System.out.println("        - application/json");
            System.out.println("      parameters:");
            System.out.println("        - in: path");
            System.out.println("          name: id");
            System.out.println("          type: integer");
            System.out.println("          format: int32");
            System.out.println("          required: true");
            System.out.println("        - in: body");
            System.out.println("          name: body");
            System.out.println("          required: true");
            System.out.println("          schema:");
            System.out
                    .println("            $ref: '#/definitions/" + path + "'");
            System.out.println("      responses:");
            System.out.println("        default:");
            System.out.println("          description: successful operation");
            System.out.println("        '404':");
            System.out.println("          description: Object not found");
            System.out.println("        '405':");
            System.out.println("          description: Validation exception");

            System.out.println("    delete:");
            System.out.println("      tags:");
            System.out.println("        - " + path);
            System.out.println("      summary: delete " + path);
            System.out.println(
                    "      description: delete object with specified ID.");
            System.out.println("      operationId: delete" + path);
            System.out.println("      produces:");
            System.out.println("        - application/xml");
            System.out.println("        - application/json");
            System.out.println("      parameters:");
            System.out.println("        - in: path");
            System.out.println("          name: id");
            System.out.println("          type: integer");
            System.out.println("          format: int32");
            System.out.println("          required: true");
            System.out.println("      responses:");
            System.out.println("        default:");
            System.out.println("          description: successful operation");
            System.out.println("        '404':");
            System.out.println("          description: Object not found");
            System.out.println("        '405':");
            System.out.println("          description: Validation exception");

        }
        System.out.println("definitions:");
        for (String path : args)
        {
            System.out.println("  " + path + ":");
            System.out.println("    type: object");
            System.out.println("    description: xxxxxxxx.");
            System.out.println("    required:");
            System.out.println("      - id");
            System.out.println("    properties:");
            System.out.println("      id:");
            System.out.println("        type: integer");
            System.out.println("        format: int32");
        }

    }

}
