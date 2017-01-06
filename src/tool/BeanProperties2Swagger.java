package tool;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.Stream;

public class BeanProperties2Swagger {

	public static void main(String[] args) {
		String fileName = args[0];
		StringBuilder output = new StringBuilder("properties:");
		StringBuilder example = new StringBuilder();
		Stream<String> stream  = null;
		String sep = System.getProperty("line.separator");
		 try {
			 stream = Files.lines(Paths.get(fileName)) ;
			Iterator<String> result = stream.iterator();
			while(result.hasNext()){
				
				String line = result.next().trim();
				if(line.length()==0){
					continue;
				}
				
				if(line.startsWith("private String ")){
					output.append(sep);
					output.append("  ");
					output.append(line.substring(15, line.indexOf(";")));
					output.append(":");
					output.append(sep);
					output.append("    ");
					output.append("type: string");
					
					example.append(sep);
					example.append(line.substring(15, line.indexOf(";")));
					example.append(" : sample value,");
				}
				if(line.startsWith("private Integer ")){
					output.append(sep);
					output.append("  ");
					output.append(line.substring(16, line.indexOf(";")));
					output.append(":");
					output.append(sep);
					output.append("    ");
					output.append("type: integer");
					output.append(sep);
					output.append("    ");
					output.append("format: int32");
					
					example.append(sep);
					example.append(line.substring(16, line.indexOf(";")));
					example.append(" : 11,");
				}
				if(line.startsWith("private Boolean ")){
					output.append(sep);
					output.append("  ");
					output.append(line.substring(16, line.indexOf(";")));
					output.append(":");
					output.append(sep);
					output.append("    ");
					output.append("type: boolean");
					
					example.append(sep);
					example.append(line.substring(16, line.indexOf(";")));
					example.append(" : true,");
				}
				if(line.startsWith("private int ")){
					output.append(sep);
					output.append("  ");
					output.append(line.substring(12, line.indexOf(";")));
					output.append(":");
					output.append(sep);
					output.append("    ");
					output.append("type: integer");
					output.append(sep);
					output.append("    ");
					output.append("format: int32");
					
					example.append(sep);
					example.append(line.substring(12, line.indexOf(";")));
					example.append(" : 1,");
				}
				if(line.startsWith("private Long ")){
					output.append(sep);
					output.append("  ");
					output.append(line.substring(13, line.indexOf(";")));
					output.append(":");
					output.append(sep);
					output.append("    ");
					output.append("type: integer");
					output.append(sep);
					output.append("    ");
					output.append("format: int64");
					
					example.append(sep);
					example.append(line.substring(13, line.indexOf(";")));
					example.append(" : 1000l,");
				}
				if(line.startsWith("private Date ")){
					output.append(sep);
					output.append("  ");
					output.append(line.substring(13, line.indexOf(";")));
					output.append(":");
					output.append(sep);
					output.append("    ");
					output.append("type: string");
					output.append(sep);
					output.append("    ");
					output.append("format: date");
					
					example.append(sep);
					example.append(line.substring(13, line.indexOf(";")));
					example.append(" : 201701040828,");
				}
				if(line.startsWith("private Float ")){
					output.append(sep);
					output.append("  ");
					output.append(line.substring(14, line.indexOf(";")));
					output.append(":");
					output.append(sep);
					output.append("    ");
					output.append("type: number");
					output.append(sep);
					output.append("    ");
					output.append("format: float");
					
					example.append(sep);
					example.append(line.substring(14, line.indexOf(";")));
					example.append(" : 3.14,");
				}
				if(line.startsWith("private Double ")){
					output.append(sep);
					output.append("  ");
					output.append(line.substring(15, line.indexOf(";")));
					output.append(":");
					output.append(sep);
					output.append("    ");
					output.append("type: number");
					output.append(sep);
					output.append("    ");
					output.append("format: double");
					
					example.append(sep);
					example.append(line.substring(15, line.indexOf(";")));
					example.append(" : 0.314,");
				}
				if(line.startsWith("private byte ")){
					output.append(sep);
					output.append("  ");
					output.append(line.substring(13, line.indexOf(";")));
					output.append(":");
					output.append(sep);
					output.append("    ");
					output.append("type: string");
					output.append(sep);
					output.append("    ");
					output.append("format: byte");
					
					example.append(sep);
					example.append(line.substring(13, line.indexOf(";")));
					example.append(" : 1,");
				}
				if(line.startsWith("private short ")){
					output.append(sep);
					output.append("  ");
					output.append(line.substring(14, line.indexOf(";")));
					output.append(":");
					output.append(sep);
					output.append("    ");
					output.append("type: integer");
					output.append(sep);
					output.append("    ");
					output.append("format: int32");
					
					example.append(sep);
					example.append(line.substring(13, line.indexOf(";")));
					example.append(" : 1,");
				}
			}
			System.out.println(output.toString());
			
			System.out.println("********************************************");
			
			System.out.println(example.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(stream!=null)
			stream.close();
		}
				
	}

}
