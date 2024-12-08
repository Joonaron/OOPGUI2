package Assignment62.Controller;

import Assignment62.Model.CurrencyModel;
import Assignment62.View.CurrencyConverterView;
import Assignment62.dao.DAO;

public class Controller {
    private CurrencyConverterView view;
    private CurrencyModel model;
    private DAO dao;

    public Controller(CurrencyConverterView view, CurrencyModel model, DAO dao) {
        this.view = view;
        this.model = model;
        this.dao = dao;
        initialize();
    }

    private void initialize() {
        view.getConvertButton().setOnAction(event -> convert());
    }

    private void convert() {
        System.out.println("Convert button pressed");
        try {
            double amount = Double.parseDouble(view.getAmountField().getText());
            String source = view.getSourceCurrencyBox().getValue();
            String result = view.getResultCurrencyBox().getValue();

            if (source != null && result != null) {
                double sourceRate = dao.getExchangeRate(source);
                double resultRate = dao.getExchangeRate(result);
                CurrencyModel sourceModel = new CurrencyModel(source, "", sourceRate);
                CurrencyModel resultModel = new CurrencyModel(result, "", resultRate);
                double convertedAmount = model.convert(amount, resultModel);
                view.getResultField().setText(String.valueOf(convertedAmount));
            }

        } catch (NumberFormatException e) {
            view.displayError("Invalid amount entered.");
        }
    }
}