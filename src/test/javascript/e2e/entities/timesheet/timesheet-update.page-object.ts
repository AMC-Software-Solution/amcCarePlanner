import { element, by, ElementFinder, protractor } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class TimesheetUpdatePage {
  pageTitle: ElementFinder = element(by.id('carePlannerApp.timesheet.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  descriptionInput: ElementFinder = element(by.css('input#timesheet-description'));
  timesheetDateInput: ElementFinder = element(by.css('input#timesheet-timesheetDate'));
  startTimeInput: ElementFinder = element(by.css('input#timesheet-startTime'));
  endTimeInput: ElementFinder = element(by.css('input#timesheet-endTime'));
  hoursWorkedInput: ElementFinder = element(by.css('input#timesheet-hoursWorked'));
  breakStartTimeInput: ElementFinder = element(by.css('input#timesheet-breakStartTime'));
  breakEndTimeInput: ElementFinder = element(by.css('input#timesheet-breakEndTime'));
  noteInput: ElementFinder = element(by.css('input#timesheet-note'));
  lastUpdatedDateInput: ElementFinder = element(by.css('input#timesheet-lastUpdatedDate'));
  clientIdInput: ElementFinder = element(by.css('input#timesheet-clientId'));
  serviceOrderSelect: ElementFinder = element(by.css('select#timesheet-serviceOrder'));
  serviceUserSelect: ElementFinder = element(by.css('select#timesheet-serviceUser'));
  careProviderSelect: ElementFinder = element(by.css('select#timesheet-careProvider'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setDescriptionInput(description) {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput() {
    return this.descriptionInput.getAttribute('value');
  }

  async setTimesheetDateInput(timesheetDate) {
    await this.timesheetDateInput.sendKeys(timesheetDate);
  }

  async getTimesheetDateInput() {
    return this.timesheetDateInput.getAttribute('value');
  }

  async setStartTimeInput(startTime) {
    await this.startTimeInput.sendKeys(startTime);
  }

  async getStartTimeInput() {
    return this.startTimeInput.getAttribute('value');
  }

  async setEndTimeInput(endTime) {
    await this.endTimeInput.sendKeys(endTime);
  }

  async getEndTimeInput() {
    return this.endTimeInput.getAttribute('value');
  }

  async setHoursWorkedInput(hoursWorked) {
    await this.hoursWorkedInput.sendKeys(hoursWorked);
  }

  async getHoursWorkedInput() {
    return this.hoursWorkedInput.getAttribute('value');
  }

  async setBreakStartTimeInput(breakStartTime) {
    await this.breakStartTimeInput.sendKeys(breakStartTime);
  }

  async getBreakStartTimeInput() {
    return this.breakStartTimeInput.getAttribute('value');
  }

  async setBreakEndTimeInput(breakEndTime) {
    await this.breakEndTimeInput.sendKeys(breakEndTime);
  }

  async getBreakEndTimeInput() {
    return this.breakEndTimeInput.getAttribute('value');
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

  async careProviderSelectLastOption() {
    await this.careProviderSelect.all(by.tagName('option')).last().click();
  }

  async careProviderSelectOption(option) {
    await this.careProviderSelect.sendKeys(option);
  }

  getCareProviderSelect() {
    return this.careProviderSelect;
  }

  async getCareProviderSelectedOption() {
    return this.careProviderSelect.element(by.css('option:checked')).getText();
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
    await this.setTimesheetDateInput('01-01-2001');
    expect(await this.getTimesheetDateInput()).to.eq('2001-01-01');
    await waitUntilDisplayed(this.saveButton);
    await this.setStartTimeInput('startTime');
    expect(await this.getStartTimeInput()).to.match(/startTime/);
    await waitUntilDisplayed(this.saveButton);
    await this.setEndTimeInput('endTime');
    expect(await this.getEndTimeInput()).to.match(/endTime/);
    await waitUntilDisplayed(this.saveButton);
    await this.setHoursWorkedInput('5');
    expect(await this.getHoursWorkedInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setBreakStartTimeInput('breakStartTime');
    expect(await this.getBreakStartTimeInput()).to.match(/breakStartTime/);
    await waitUntilDisplayed(this.saveButton);
    await this.setBreakEndTimeInput('breakEndTime');
    expect(await this.getBreakEndTimeInput()).to.match(/breakEndTime/);
    await waitUntilDisplayed(this.saveButton);
    await this.setNoteInput('note');
    expect(await this.getNoteInput()).to.match(/note/);
    await waitUntilDisplayed(this.saveButton);
    await this.setLastUpdatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getLastUpdatedDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setClientIdInput('5');
    expect(await this.getClientIdInput()).to.eq('5');
    await this.serviceOrderSelectLastOption();
    await this.serviceUserSelectLastOption();
    await this.careProviderSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
