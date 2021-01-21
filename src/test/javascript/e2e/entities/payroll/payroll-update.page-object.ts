import { element, by, ElementFinder, protractor } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class PayrollUpdatePage {
  pageTitle: ElementFinder = element(by.id('carePlannerApp.payroll.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  paymentDateInput: ElementFinder = element(by.css('input#payroll-paymentDate'));
  payPeriodInput: ElementFinder = element(by.css('input#payroll-payPeriod'));
  totalHoursWorkedInput: ElementFinder = element(by.css('input#payroll-totalHoursWorked'));
  grossPayInput: ElementFinder = element(by.css('input#payroll-grossPay'));
  netPayInput: ElementFinder = element(by.css('input#payroll-netPay'));
  totalTaxInput: ElementFinder = element(by.css('input#payroll-totalTax'));
  payrollStatusSelect: ElementFinder = element(by.css('select#payroll-payrollStatus'));
  createdDateInput: ElementFinder = element(by.css('input#payroll-createdDate'));
  lastUpdatedDateInput: ElementFinder = element(by.css('input#payroll-lastUpdatedDate'));
  clientIdInput: ElementFinder = element(by.css('input#payroll-clientId'));
  hasExtraDataInput: ElementFinder = element(by.css('input#payroll-hasExtraData'));
  employeeSelect: ElementFinder = element(by.css('select#payroll-employee'));
  timesheetSelect: ElementFinder = element(by.css('select#payroll-timesheet'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setPaymentDateInput(paymentDate) {
    await this.paymentDateInput.sendKeys(paymentDate);
  }

  async getPaymentDateInput() {
    return this.paymentDateInput.getAttribute('value');
  }

  async setPayPeriodInput(payPeriod) {
    await this.payPeriodInput.sendKeys(payPeriod);
  }

  async getPayPeriodInput() {
    return this.payPeriodInput.getAttribute('value');
  }

  async setTotalHoursWorkedInput(totalHoursWorked) {
    await this.totalHoursWorkedInput.sendKeys(totalHoursWorked);
  }

  async getTotalHoursWorkedInput() {
    return this.totalHoursWorkedInput.getAttribute('value');
  }

  async setGrossPayInput(grossPay) {
    await this.grossPayInput.sendKeys(grossPay);
  }

  async getGrossPayInput() {
    return this.grossPayInput.getAttribute('value');
  }

  async setNetPayInput(netPay) {
    await this.netPayInput.sendKeys(netPay);
  }

  async getNetPayInput() {
    return this.netPayInput.getAttribute('value');
  }

  async setTotalTaxInput(totalTax) {
    await this.totalTaxInput.sendKeys(totalTax);
  }

  async getTotalTaxInput() {
    return this.totalTaxInput.getAttribute('value');
  }

  async setPayrollStatusSelect(payrollStatus) {
    await this.payrollStatusSelect.sendKeys(payrollStatus);
  }

  async getPayrollStatusSelect() {
    return this.payrollStatusSelect.element(by.css('option:checked')).getText();
  }

  async payrollStatusSelectLastOption() {
    await this.payrollStatusSelect.all(by.tagName('option')).last().click();
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
  async employeeSelectLastOption() {
    await this.employeeSelect.all(by.tagName('option')).last().click();
  }

  async employeeSelectOption(option) {
    await this.employeeSelect.sendKeys(option);
  }

  getEmployeeSelect() {
    return this.employeeSelect;
  }

  async getEmployeeSelectedOption() {
    return this.employeeSelect.element(by.css('option:checked')).getText();
  }

  async timesheetSelectLastOption() {
    await this.timesheetSelect.all(by.tagName('option')).last().click();
  }

  async timesheetSelectOption(option) {
    await this.timesheetSelect.sendKeys(option);
  }

  getTimesheetSelect() {
    return this.timesheetSelect;
  }

  async getTimesheetSelectedOption() {
    return this.timesheetSelect.element(by.css('option:checked')).getText();
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
    await this.setPaymentDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getPaymentDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setPayPeriodInput('payPeriod');
    expect(await this.getPayPeriodInput()).to.match(/payPeriod/);
    await waitUntilDisplayed(this.saveButton);
    await this.setTotalHoursWorkedInput('5');
    expect(await this.getTotalHoursWorkedInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setGrossPayInput('grossPay');
    expect(await this.getGrossPayInput()).to.match(/grossPay/);
    await waitUntilDisplayed(this.saveButton);
    await this.setNetPayInput('netPay');
    expect(await this.getNetPayInput()).to.match(/netPay/);
    await waitUntilDisplayed(this.saveButton);
    await this.setTotalTaxInput('totalTax');
    expect(await this.getTotalTaxInput()).to.match(/totalTax/);
    await waitUntilDisplayed(this.saveButton);
    await this.payrollStatusSelectLastOption();
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
    await this.employeeSelectLastOption();
    await this.timesheetSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
