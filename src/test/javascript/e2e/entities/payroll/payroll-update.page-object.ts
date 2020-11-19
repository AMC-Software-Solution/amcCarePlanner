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
  lastUpdatedDateInput: ElementFinder = element(by.css('input#payroll-lastUpdatedDate'));
  tenantIdInput: ElementFinder = element(by.css('input#payroll-tenantId'));
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
  async setLastUpdatedDateInput(lastUpdatedDate) {
    await this.lastUpdatedDateInput.sendKeys(lastUpdatedDate);
  }

  async getLastUpdatedDateInput() {
    return this.lastUpdatedDateInput.getAttribute('value');
  }

  async setTenantIdInput(tenantId) {
    await this.tenantIdInput.sendKeys(tenantId);
  }

  async getTenantIdInput() {
    return this.tenantIdInput.getAttribute('value');
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
    await this.setGrossPayInput('5');
    expect(await this.getGrossPayInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setNetPayInput('5');
    expect(await this.getNetPayInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setTotalTaxInput('5');
    expect(await this.getTotalTaxInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.payrollStatusSelectLastOption();
    await waitUntilDisplayed(this.saveButton);
    await this.setLastUpdatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getLastUpdatedDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setTenantIdInput('5');
    expect(await this.getTenantIdInput()).to.eq('5');
    await this.employeeSelectLastOption();
    await this.timesheetSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
