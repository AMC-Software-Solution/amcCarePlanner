import { element, by, ElementFinder, protractor } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class TaskUpdatePage {
  pageTitle: ElementFinder = element(by.id('carePlannerApp.task.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  taskNameInput: ElementFinder = element(by.css('input#task-taskName'));
  descriptionInput: ElementFinder = element(by.css('input#task-description'));
  dateOfTaskInput: ElementFinder = element(by.css('input#task-dateOfTask'));
  startTimeInput: ElementFinder = element(by.css('input#task-startTime'));
  endTimeInput: ElementFinder = element(by.css('input#task-endTime'));
  statusSelect: ElementFinder = element(by.css('select#task-status'));
  dateCreatedInput: ElementFinder = element(by.css('input#task-dateCreated'));
  lastUpdatedDateInput: ElementFinder = element(by.css('input#task-lastUpdatedDate'));
  tenantIdInput: ElementFinder = element(by.css('input#task-tenantId'));
  serviceUserSelect: ElementFinder = element(by.css('select#task-serviceUser'));
  assignedToSelect: ElementFinder = element(by.css('select#task-assignedTo'));
  serviceOrderSelect: ElementFinder = element(by.css('select#task-serviceOrder'));
  createdBySelect: ElementFinder = element(by.css('select#task-createdBy'));
  allocatedBySelect: ElementFinder = element(by.css('select#task-allocatedBy'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setTaskNameInput(taskName) {
    await this.taskNameInput.sendKeys(taskName);
  }

  async getTaskNameInput() {
    return this.taskNameInput.getAttribute('value');
  }

  async setDescriptionInput(description) {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput() {
    return this.descriptionInput.getAttribute('value');
  }

  async setDateOfTaskInput(dateOfTask) {
    await this.dateOfTaskInput.sendKeys(dateOfTask);
  }

  async getDateOfTaskInput() {
    return this.dateOfTaskInput.getAttribute('value');
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

  async setStatusSelect(status) {
    await this.statusSelect.sendKeys(status);
  }

  async getStatusSelect() {
    return this.statusSelect.element(by.css('option:checked')).getText();
  }

  async statusSelectLastOption() {
    await this.statusSelect.all(by.tagName('option')).last().click();
  }
  async setDateCreatedInput(dateCreated) {
    await this.dateCreatedInput.sendKeys(dateCreated);
  }

  async getDateCreatedInput() {
    return this.dateCreatedInput.getAttribute('value');
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

  async createdBySelectLastOption() {
    await this.createdBySelect.all(by.tagName('option')).last().click();
  }

  async createdBySelectOption(option) {
    await this.createdBySelect.sendKeys(option);
  }

  getCreatedBySelect() {
    return this.createdBySelect;
  }

  async getCreatedBySelectedOption() {
    return this.createdBySelect.element(by.css('option:checked')).getText();
  }

  async allocatedBySelectLastOption() {
    await this.allocatedBySelect.all(by.tagName('option')).last().click();
  }

  async allocatedBySelectOption(option) {
    await this.allocatedBySelect.sendKeys(option);
  }

  getAllocatedBySelect() {
    return this.allocatedBySelect;
  }

  async getAllocatedBySelectedOption() {
    return this.allocatedBySelect.element(by.css('option:checked')).getText();
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
    await this.setTaskNameInput('taskName');
    expect(await this.getTaskNameInput()).to.match(/taskName/);
    await waitUntilDisplayed(this.saveButton);
    await this.setDescriptionInput('description');
    expect(await this.getDescriptionInput()).to.match(/description/);
    await waitUntilDisplayed(this.saveButton);
    await this.setDateOfTaskInput('01-01-2001');
    expect(await this.getDateOfTaskInput()).to.eq('2001-01-01');
    await waitUntilDisplayed(this.saveButton);
    await this.setStartTimeInput('startTime');
    expect(await this.getStartTimeInput()).to.match(/startTime/);
    await waitUntilDisplayed(this.saveButton);
    await this.setEndTimeInput('endTime');
    expect(await this.getEndTimeInput()).to.match(/endTime/);
    await waitUntilDisplayed(this.saveButton);
    await this.statusSelectLastOption();
    await waitUntilDisplayed(this.saveButton);
    await this.setDateCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getDateCreatedInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setLastUpdatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getLastUpdatedDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setTenantIdInput('5');
    expect(await this.getTenantIdInput()).to.eq('5');
    await this.serviceUserSelectLastOption();
    await this.assignedToSelectLastOption();
    await this.serviceOrderSelectLastOption();
    await this.createdBySelectLastOption();
    await this.allocatedBySelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
