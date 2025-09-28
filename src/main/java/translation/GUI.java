package translation;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;


// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CountryAndLanguagesLists lists = new CountryAndLanguagesLists();
            List<String> countries = lists.getCountries();
            List<String> languages = lists.getLanguages();

            JPanel countryPanel = new JPanel();
            JList<String> countryField = new JList<>(countries.toArray(new String[0]));
            countryPanel.add(new JLabel("Country:"));
            countryField.setVisibleRowCount(5);
            countryPanel.add(new JScrollPane(countryField));

            JPanel languagePanel = new JPanel();
            JList<String> languageField = new JList<>(languages.toArray(new String[0]));
            languagePanel.add(new JLabel("Language:"));
            languageField.setVisibleRowCount(5);
            languagePanel.add(new JScrollPane(languageField));

            JPanel buttonPanel = new JPanel();
            JButton submit = new JButton("Submit");
            buttonPanel.add(submit);

            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);


            // adding listener for when the user clicks the submit button
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CountryCodeConverter countryConverter = new CountryCodeConverter("country-codes.txt");
                    LanguageCodeConverter languageConverter = new LanguageCodeConverter("language-codes.txt");
                    String language = languageConverter.fromLanguage(languageField.getSelectedValue());
                    String country = countryConverter.fromCountry(countryField.getSelectedValue()).toLowerCase();

                    // for now, just using our simple translator, but
                    // we'll need to use the real JSON version later.
                    Translator translator = new JSONTranslator();

                    String result = translator.translate(country, language);
                    if (result == null) {
                        result = "no translation found!";
                    }
                    resultLabel.setText(result);

                }

            });

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(countryPanel);
            mainPanel.add(languagePanel);
            mainPanel.add(buttonPanel);

            JFrame frame = new JFrame("Country Name Translator");

            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setSize(500, 300);
            frame.setVisible(true);


        });
    }
}
