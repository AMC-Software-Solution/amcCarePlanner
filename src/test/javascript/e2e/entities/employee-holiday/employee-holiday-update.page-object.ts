import { element, by, ElementFinder, protractor } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class EmployeeHolidayUpdatePage {
  pageTitle: ElementFinder = element(by.id('carePlannerApp.employeeHoliday.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  descriptionInput: ElementFinder = element(by.css('input#employee-holiday-description'));
  startDateInput: ElementFinder = element(by.css('input#employee-holiday-startDate'));
  endDateInput: ElementFinder = element(by.css('input#employee-holiday-endDate'));
  employeeHolidayTypeSelect: ElementFinder = element(by.css('select#employee-holiday-employeeHolidayType'));
  approvedDateInput: ElementFinder = element(by.css('input#employee-holiday-approvedDate'));
  requestedDateInput: ElementFinder = element(by.css('input#employee-holiday-requestedDate'));
  approvedInput: ElementFinder = element(by.css('input#employee-holiday-approved'));
  noteInput: ElementFinder = element(by.css('input#employee-holiday-note'));
  lastUpdatedDateInput: ElementFinder = element(by.css('input#employee-holiday-lastUpdatedDate'));
  clientIdInput: ElementFinder = element(by.css('input#employee-holiday-clientId'));
  employeeSelect: ElementFinder = element(by.css('select#employee-holiday-employee'));
  approvedBySelect: ElementFinder = element(by.css('select#employee-holiday-approvedBy'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setDescriptionInput(description) {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput() {
    return this.descriptionInput.getAttribute('value');
  }

  async setStartDateInput(startDate) {
    await this.startDateInput.sendKeys(startDate);
  }

  async getStartDateInput() {
    return this.startDateInput.getAttribute('value');
  }

  async setEndDateInput(endDate) {
    await this.endDateInput.sendKeys(endDate);
  }

  async getEndDateInput() {
    return this.endDateInput.getAttribute('value');
  }

  async setEmployeeHolidayTypeSelect(employeeHolidayType) {
    await this.employeeHolidayTypeSelect.sendKeys(employeeHolidayType);
  }

  async getEmployeeHolidayTypeSelect() {
    return this.employeeHolidayTypeSelect.element(by.css('option:checked')).getText();
  }

  async employeeHolidayTypeSelectLastOption() {
    await this.employeeHolidayTypeSelect.all(by.tagName('option')).last().click();
  }
  async setApprovedDateInput(approvedDate) {
    await this.approvedDateInput.sendKeys(approvedDate);
  }

  async getApprovedDateInput() {
    return this.approvedDateInput.getAttribute('value');
  }

  async setRequestedDateInput(requestedDate) {
    await this.requestedDateInput.sendKeys(requestedDate);
  }

  async getRequestedDateInput() {
    return this.requestedDateInput.getAttribute('value');
  }

  getApprovedInput() {
    return this.approvedInput;
  }
  async setNoteInput(note) {
    await this.noteInput.sendKeys(note);
  }

  async getNoteInput() {
    return this.noteInput.getAttribute('value');
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

  async approvedBySelectLastOption() {
    await this.approvedBySelect.all(by.tagName('option')).last().click();
  }

  async approvedBySelectOption(option) {
    await this.approvedBySelect.sendKeys(option);
  }

  getApprovedBySelect() {
    return this.approvedBySelect;
  }

  async getApprovedBySelectedOption() {
    return this.approvedBySelect.element(by.css('option:checked')).getText();
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
    await this.setDescriptionInput('description');
    expect(await this.getDescriptionInput()).to.match(/description/);
    await waitUntilDisplayed(this.saveButton);
    await this.setStartDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getStartDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setEndDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getEndDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.employeeHolidayTypeSelectLastOption();
    await waitUntilDisplayed(this.saveButton);
    await this.setApprovedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getApprovedDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setRequestedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getRequestedDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    const selectedApproved = await this.getApprovedInput().isSelected();
    if (selectedApproved) {
      await this.getApprovedInput().click();
      expect(await this.getApprovedInput().isSelected()).to.be.false;
    } else {
      await this.getApprovedInput().click();
      expect(await this.getApprovedInput().isSelected()).to.be.true;
    }
    await waitUntilDisplayed(this.saveButton);
    await this.setNoteInput('note');
    expect(await this.getNoteInput()).to.match(/note/);
    await waitUntilDisplayed(this.saveButton);
    await this.setLastUpdatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getLastUpdatedDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setClientIdInput('5');
    expect(await this.getClientIdInput()).to.eq('5');
    await this.employeeSelectLastOption();
    await this.approvedBySelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
