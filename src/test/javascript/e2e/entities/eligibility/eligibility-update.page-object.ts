import { element, by, ElementFinder, protractor } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class EligibilityUpdatePage {
  pageTitle: ElementFinder = element(by.id('carePlannerApp.eligibility.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  noteInput: ElementFinder = element(by.css('input#eligibility-note'));
  lastUpdatedDateInput: ElementFinder = element(by.css('input#eligibility-lastUpdatedDate'));
  clientIdInput: ElementFinder = element(by.css('input#eligibility-clientId'));
  eligibilityTypeSelect: ElementFinder = element(by.css('select#eligibility-eligibilityType'));
  employeeSelect: ElementFinder = element(by.css('select#eligibility-employee'));

  getPageTitle() {
    return this.pageTitle;
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

  async eligibilityTypeSelectLastOption() {
    await this.eligibilityTypeSelect.all(by.tagName('option')).last().click();
  }

  async eligibilityTypeSelectOption(option) {
    await this.eligibilityTypeSelect.sendKeys(option);
  }

  getEligibilityTypeSelect() {
    return this.eligibilityTypeSelect;
  }

  async getEligibilityTypeSelectedOption() {
    return this.eligibilityTypeSelect.element(by.css('option:checked')).getText();
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
    await this.setNoteInput('note');
    expect(await this.getNoteInput()).to.match(/note/);
    await waitUntilDisplayed(this.saveButton);
    await this.setLastUpdatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getLastUpdatedDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setClientIdInput('5');
    expect(await this.getClientIdInput()).to.eq('5');
    await this.eligibilityTypeSelectLastOption();
    await this.employeeSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
