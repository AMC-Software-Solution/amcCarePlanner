import { element, by, ElementFinder, protractor } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class CarerClientRelationUpdatePage {
  pageTitle: ElementFinder = element(by.id('carePlannerApp.carerClientRelation.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  relationTypeSelect: ElementFinder = element(by.css('select#carer-client-relation-relationType'));
  reasonInput: ElementFinder = element(by.css('input#carer-client-relation-reason'));
  countInput: ElementFinder = element(by.css('input#carer-client-relation-count'));
  lastUpdatedDateInput: ElementFinder = element(by.css('input#carer-client-relation-lastUpdatedDate'));
  clientIdInput: ElementFinder = element(by.css('input#carer-client-relation-clientId'));
  employeeSelect: ElementFinder = element(by.css('select#carer-client-relation-employee'));
  serviceUserSelect: ElementFinder = element(by.css('select#carer-client-relation-serviceUser'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setRelationTypeSelect(relationType) {
    await this.relationTypeSelect.sendKeys(relationType);
  }

  async getRelationTypeSelect() {
    return this.relationTypeSelect.element(by.css('option:checked')).getText();
  }

  async relationTypeSelectLastOption() {
    await this.relationTypeSelect.all(by.tagName('option')).last().click();
  }
  async setReasonInput(reason) {
    await this.reasonInput.sendKeys(reason);
  }

  async getReasonInput() {
    return this.reasonInput.getAttribute('value');
  }

  async setCountInput(count) {
    await this.countInput.sendKeys(count);
  }

  async getCountInput() {
    return this.countInput.getAttribute('value');
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
    await this.relationTypeSelectLastOption();
    await waitUntilDisplayed(this.saveButton);
    await this.setReasonInput('reason');
    expect(await this.getReasonInput()).to.match(/reason/);
    await waitUntilDisplayed(this.saveButton);
    await this.setCountInput('5');
    expect(await this.getCountInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setLastUpdatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getLastUpdatedDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setClientIdInput('5');
    expect(await this.getClientIdInput()).to.eq('5');
    await this.employeeSelectLastOption();
    await this.serviceUserSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
