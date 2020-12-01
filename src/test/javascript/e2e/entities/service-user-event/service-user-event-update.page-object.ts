import { element, by, ElementFinder, protractor } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class ServiceUserEventUpdatePage {
  pageTitle: ElementFinder = element(by.id('carePlannerApp.serviceUserEvent.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  eventTitleInput: ElementFinder = element(by.css('input#service-user-event-eventTitle'));
  descriptionInput: ElementFinder = element(by.css('input#service-user-event-description'));
  serviceUserEventStatusSelect: ElementFinder = element(by.css('select#service-user-event-serviceUserEventStatus'));
  serviceUserEventTypeSelect: ElementFinder = element(by.css('select#service-user-event-serviceUserEventType'));
  prioritySelect: ElementFinder = element(by.css('select#service-user-event-priority'));
  noteInput: ElementFinder = element(by.css('input#service-user-event-note'));
  dateOfEventInput: ElementFinder = element(by.css('input#service-user-event-dateOfEvent'));
  lastUpdatedDateInput: ElementFinder = element(by.css('input#service-user-event-lastUpdatedDate'));
  clientIdInput: ElementFinder = element(by.css('input#service-user-event-clientId'));
  reportedBySelect: ElementFinder = element(by.css('select#service-user-event-reportedBy'));
  assignedToSelect: ElementFinder = element(by.css('select#service-user-event-assignedTo'));
  serviceUserSelect: ElementFinder = element(by.css('select#service-user-event-serviceUser'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setEventTitleInput(eventTitle) {
    await this.eventTitleInput.sendKeys(eventTitle);
  }

  async getEventTitleInput() {
    return this.eventTitleInput.getAttribute('value');
  }

  async setDescriptionInput(description) {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput() {
    return this.descriptionInput.getAttribute('value');
  }

  async setServiceUserEventStatusSelect(serviceUserEventStatus) {
    await this.serviceUserEventStatusSelect.sendKeys(serviceUserEventStatus);
  }

  async getServiceUserEventStatusSelect() {
    return this.serviceUserEventStatusSelect.element(by.css('option:checked')).getText();
  }

  async serviceUserEventStatusSelectLastOption() {
    await this.serviceUserEventStatusSelect.all(by.tagName('option')).last().click();
  }
  async setServiceUserEventTypeSelect(serviceUserEventType) {
    await this.serviceUserEventTypeSelect.sendKeys(serviceUserEventType);
  }

  async getServiceUserEventTypeSelect() {
    return this.serviceUserEventTypeSelect.element(by.css('option:checked')).getText();
  }

  async serviceUserEventTypeSelectLastOption() {
    await this.serviceUserEventTypeSelect.all(by.tagName('option')).last().click();
  }
  async setPrioritySelect(priority) {
    await this.prioritySelect.sendKeys(priority);
  }

  async getPrioritySelect() {
    return this.prioritySelect.element(by.css('option:checked')).getText();
  }

  async prioritySelectLastOption() {
    await this.prioritySelect.all(by.tagName('option')).last().click();
  }
  async setNoteInput(note) {
    await this.noteInput.sendKeys(note);
  }

  async getNoteInput() {
    return this.noteInput.getAttribute('value');
  }

  async setDateOfEventInput(dateOfEvent) {
    await this.dateOfEventInput.sendKeys(dateOfEvent);
  }

  async getDateOfEventInput() {
    return this.dateOfEventInput.getAttribute('value');
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

  async reportedBySelectLastOption() {
    await this.reportedBySelect.all(by.tagName('option')).last().click();
  }

  async reportedBySelectOption(option) {
    await this.reportedBySelect.sendKeys(option);
  }

  getReportedBySelect() {
    return this.reportedBySelect;
  }

  async getReportedBySelectedOption() {
    return this.reportedBySelect.element(by.css('option:checked')).getText();
  }

  async assignedToSelectLastOption() {
    await this.assignedToSelect.all(by.tagName('option')).last().click();
  }

  async assignedToSelectOption(option) {
    await this.assignedToSelect.sendKeys(option);
  }

  getAssignedToSelect() {
    return this.assignedToSelect;
  }

  async getAssignedToSelectedOption() {
    return this.assignedToSelect.element(by.css('option:checked')).getText();
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
    await this.setEventTitleInput('eventTitle');
    expect(await this.getEventTitleInput()).to.match(/eventTitle/);
    await waitUntilDisplayed(this.saveButton);
    await this.setDescriptionInput('description');
    expect(await this.getDescriptionInput()).to.match(/description/);
    await waitUntilDisplayed(this.saveButton);
    await this.serviceUserEventStatusSelectLastOption();
    await waitUntilDisplayed(this.saveButton);
    await this.serviceUserEventTypeSelectLastOption();
    await waitUntilDisplayed(this.saveButton);
    await this.prioritySelectLastOption();
    await waitUntilDisplayed(this.saveButton);
    await this.setNoteInput('note');
    expect(await this.getNoteInput()).to.match(/note/);
    await waitUntilDisplayed(this.saveButton);
    await this.setDateOfEventInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getDateOfEventInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setLastUpdatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getLastUpdatedDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setClientIdInput('5');
    expect(await this.getClientIdInput()).to.eq('5');
    await this.reportedBySelectLastOption();
    await this.assignedToSelectLastOption();
    await this.serviceUserSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
