package exception;

/** Signifie qu'il y a eu un problème spécifique à l'import ou l'export du csv
 * @author Lucas Martineau
 */
public class ImportExportException extends Exception{
    public ImportExportException(String s){
        super(s);
    }
}
