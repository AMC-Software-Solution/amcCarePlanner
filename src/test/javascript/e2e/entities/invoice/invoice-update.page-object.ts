import { element, by, ElementFinder, protractor } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class InvoiceUpdatePage {
  pageTitle: ElementFinder = element(by.id('carePlannerApp.invoice.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  totalAmountInput: ElementFinder = element(by.css('input#invoice-totalAmount'));
  descriptionInput: ElementFinder = element(by.css('input#invoice-description'));
  invoiceNumberInput: ElementFinder = element(by.css('input#invoice-invoiceNumber'));
  generatedDateInput: ElementFinder = element(by.css('input#invoice-generatedDate'));
  dueDateInput: ElementFinder = element(by.css('input#invoice-dueDate'));
  paymentDateInput: ElementFinder = element(by.css('input#invoice-paymentDate'));
  invoiceStatusSelect: ElementFinder = element(by.css('select#invoice-invoiceStatus'));
  taxInput: ElementFinder = element(by.css('input#invoice-tax'));
  attribute1Input: ElementFinder = element(by.css('input#invoice-attribute1'));
  attribute2Input: ElementFinder = element(by.css('input#invoice-attribute2'));
  attribute3Input: ElementFinder = element(by.css('input#invoice-attribute3'));
  attribute4Input: ElementFinder = element(by.css('input#invoice-attribute4'));
  attribute5Input: ElementFinder = element(by.css('input#invoice-attribute5'));
  attribute6Input: ElementFinder = element(by.css('input#invoice-attribute6'));
  attribute7Input: ElementFinder = element(by.css('input#invoice-attribute7'));
  createdDateInput: ElementFinder = element(by.css('input#invoice-createdDate'));
  lastUpdatedDateInput: ElementFinder = element(by.css('input#invoice-lastUpdatedDate'));
  clientIdInput: ElementFinder = element(by.css('input#invoice-clientId'));
  hasExtraDataInput: ElementFinder = element(by.css('input#invoice-hasExtraData'));
  serviceOrderSelect: ElementFinder = element(by.css('select#invoice-serviceOrder'));
  serviceUserSelect: ElementFinder = element(by.css('select#invoice-serviceUser'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setTotalAmountInput(totalAmount) {
    await this.totalAmountInput.sendKeys(totalAmount);
  }

  async getTotalAmountInput() {
    return this.totalAmountInput.getAttribute('value');
  }

  async setDescriptionInput(description) {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput() {
    return this.descriptionInput.getAttribute('value');
  }

  async setInvoiceNumberInput(invoiceNumber) {
    await this.invoiceNumberInput.sendKeys(invoiceNumber);
  }

  async getInvoiceNumberInput() {
    return this.invoiceNumberInput.getAttribute('value');
  }

  async setGeneratedDateInput(generatedDate) {
    await this.generatedDateInput.sendKeys(generatedDate);
  }

  async getGeneratedDateInput() {
    return this.generatedDateInput.getAttribute('value');
  }

  async setDueDateInput(dueDate) {
    await this.dueDateInput.sendKeys(dueDate);
  }

  async getDueDateInput() {
    return this.dueDateInput.getAttribute('value');
  }

  async setPaymentDateInput(paymentDate) {
    await this.paymentDateInput.sendKeys(paymentDate);
  }

  async getPaymentDateInput() {
    return this.paymentDateInput.getAttribute('value');
  }

  async setInvoiceStatusSelect(invoiceStatus) {
    await this.invoiceStatusSelect.sendKeys(invoiceStatus);
  }

  async getInvoiceStatusSelect() {
    return this.invoiceStatusSelect.element(by.css('option:checked')).getText();
  }

  async invoiceStatusSelectLastOption() {
    await this.invoiceStatusSelect.all(by.tagName('option')).last().click();
  }
  async setTaxInput(tax) {
    await this.taxInput.sendKeys(tax);
  }

  async getTaxInput() {
    return this.taxInput.getAttribute('value');
  }

  async setAttribute1Input(attribute1) {
    await this.attribute1Input.sendKeys(attribute1);
  }

  async getAttribute1Input() {
    return this.attribute1Input.getAttribute('value');
  }

  async setAttribute2Input(attribute2) {
    await this.attribute2Input.sendKeys(attribute2);
  }

  async getAttribute2Input() {
    return this.attribute2Input.getAttribute('value');
  }

  async setAttribute3Input(attribute3) {
    await this.attribute3Input.sendKeys(attribute3);
  }

  async getAttribute3Input() {
    return this.attribute3Input.getAttribute('value');
  }

  async setAttribute4Input(attribute4) {
    await this.attribute4Input.sendKeys(attribute4);
  }

  async getAttribute4Input() {
    return this.attribute4Input.getAttribute('value');
  }

  async setAttribute5Input(attribute5) {
    await this.attribute5Input.sendKeys(attribute5);
  }

  async getAttribute5Input() {
    return this.attribute5Input.getAttribute('value');
  }

  async setAttribute6Input(attribute6) {
    await this.attribute6Input.sendKeys(attribute6);
  }

  async getAttribute6Input() {
    return this.attribute6Input.getAttribute('value');
  }

  async setAttribute7Input(attribute7) {
    await this.attribute7Input.sendKeys(attribute7);
  }

  async getAttribute7Input() {
    return this.attribute7Input.getAttribute('value');
  }

  async setCreatedDateInput(createdDate) {
    await this.createdDateInput.sendKeys(createdDate);
  }

  async getCreatedDateInput() {
    return this.createdDateInput.getAttribute('value');
  }

  async setLastUpdatedDateInput(lastUpdatedDate) {
    await this.lastUpdatedDateInput.sendKeys(lastUpdatedDate);
  }

  async getLastUpdatedDateInput() {
    return this.lastUpdatedDateInput.getAttribute('value');
  }

  async setClientIdInput(clientId) {
    await this.clientIdInput.sendKeys(clientId);
  }

  async getClientIdInput() {
    return this.clientIdInput.getAttribute('value');
  }

  getHasExtraDataInput() {
    return this.hasExtraDataInput;
  }
  async serviceOrderSelectLastOption() {
    await this.serviceOrderSelect.all(by.tagName('option')).last().click();
  }

  async serviceOrderSelectOption(option) {
    await this.serviceOrderSelect.sendKeys(option);
  }

  getServiceOrderSelect() {
    return this.serviceOrderSelect;
  }

  async getServiceOrderSelectedOption() {
    return this.serviceOrderSelect.element(by.css('option:checked')).getText();
  }

  async serviceUserSelectLastOption() {
    await this.serviceUserSelect.all(by.tagName('option')).last().click();
  }

  async serviceUserSelectOption(option) {
    await this.serviceUserSelect.sendKeys(option);
  }

  getServiceUserSelect() {
    return this.serviceUserSelect;
  }

  async getServiceUserSelectedOption() {
    return this.serviceUserSelect.element(by.css('option:checked')).getText();
  }

  async save() {
    await this.saveButton.click();
  }

  async cancel() {
    await this.cancelButton.click();
  }

  getSaveButton() {
    return this.saveButton;
  }

  async enterData() {
    await waitUntilDisplayed(this.saveButton);
    await this.setTotalAmountInput('5');
    expect(await this.getTotalAmountInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setDescriptionInput('description');
    expect(await this.getDescriptionInput()).to.match(/description/);
    await waitUntilDisplayed(this.saveButton);
    await this.setInvoiceNumberInput('64c99148-3908-465d-8c4a-e510e3ade974');
    expect(await this.getInvoiceNumberInput()).to.match(/64c99148-3908-465d-8c4a-e510e3ade974/);
    await waitUntilDisplayed(this.saveButton);
    await this.setGeneratedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getGeneratedDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setDueDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getDueDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setPaymentDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getPaymentDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.invoiceStatusSelectLastOption();
    await waitUntilDisplayed(this.saveButton);
    await this.setTaxInput('5');
    expect(await this.getTaxInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setAttribute1Input('attribute1');
    expect(await this.getAttribute1Input()).to.match(/attribute1/);
    await waitUntilDisplayed(this.saveButton);
    await this.setAttribute2Input('attribute2');
    expect(await this.getAttribute2Input()).to.match(/attribute2/);
    await waitUntilDisplayed(this.saveButton);
    await this.setAttribute3Input('attribute3');
    expect(await this.getAttribute3Input()).to.match(/attribute3/);
    await waitUntilDisplayed(this.saveButton);
    await this.setAttribute4Input('attribute4');
    expect(await this.getAttribute4Input()).to.match(/attribute4/);
    await waitUntilDisplayed(this.saveButton);
    await this.setAttribute5Input('attribute5');
    expect(await this.getAttribute5Input()).to.match(/attribute5/);
    await waitUntilDisplayed(this.saveButton);
    await this.setAttribute6Input('attribute6');
    expect(await this.getAttribute6Input()).to.match(/attribute6/);
    await waitUntilDisplayed(this.saveButton);
    await this.setAttribute7Input('attribute7');
    expect(await this.getAttribute7Input()).to.match(/attribute7/);
    await waitUntilDisplayed(this.saveButton);
    await this.setCreatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getCreatedDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setLastUpdatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getLastUpdatedDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setClientIdInput('5');
    expect(await this.getClientIdInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    const selectedHasExtraData = await this.getHasExtraDataInput().isSelected();
    if (selectedHasExtraData) {
      await this.getHasExtraDataInput().click();
      expect(await this.getHasExtraDataInput().isSelected()).to.be.false;
    } else {
      await this.getHasExtraDataInput().click();
      expect(await this.getHasExtraDataInput().isSelected()).to.be.true;
    }
    await this.serviceOrderSelectLastOption();
    await this.serviceUserSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
