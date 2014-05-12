package wowlist.misc;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/**
 * A JTextField that accepts only integers.
 *
 * @author David Buzatto
 * @source http://stackoverflow.com/questions/14319064/how-to-validate-a-jtextfield-to-accept-only-interger-numbers
 */
public class IntegerField extends JTextField {

    public IntegerField() {
        super();
    }
    
    public IntegerField(String amountFill) {
		setText(amountFill);
	}


    public IntegerField( int cols ) {
        super( cols );
    }

   
	@Override
    protected Document createDefaultModel() {
        return new UpperCaseDocument();
    }

    static class UpperCaseDocument extends PlainDocument {

        @Override
        public void insertString( int offs, String str, AttributeSet a )
                throws BadLocationException {

            if ( str == null ) {
                return;
            }

            char[] chars = str.toCharArray();
            boolean ok = true;

            for ( int i = 0; i < chars.length; i++ ) {

                try {
                    Integer.parseInt( String.valueOf( chars[i] ) );
                } catch ( NumberFormatException exc ) {
                    ok = false;
                    break;
                }


            }

            if ( ok )
                super.insertString( offs, new String( chars ), a );

        }
    }

}