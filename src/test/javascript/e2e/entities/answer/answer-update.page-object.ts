import { element, by, ElementFinder, protractor } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class AnswerUpdatePage {
  pageTitle: ElementFinder = element(by.id('carePlannerApp.answer.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  answerInput: ElementFinder = element(by.css('input#answer-answer'));
  descriptionInput: ElementFinder = element(by.css('input#answer-description'));
  attribute1Input: ElementFinder = element(by.css('input#answer-attribute1'));
  attribute2Input: ElementFinder = element(by.css('input#answer-attribute2'));
  attribute3Input: ElementFinder = element(by.css('input#answer-attribute3'));
  attribute4Input: ElementFinder = element(by.css('input#answer-attribute4'));
  attribute5Input: ElementFinder = element(by.css('input#answer-attribute5'));
  createdDateInput: ElementFinder = element(by.css('input#answer-createdDate'));
  lastUpdatedDateInput: ElementFinder = element(by.css('input#answer-lastUpdatedDate'));
  tenantIdInput: ElementFinder = element(by.css('input#answer-tenantId'));
  questionSelect: ElementFinder = element(by.css('select#answer-question'));
  serviceUserSelect: ElementFinder = element(by.css('select#answer-serviceUser'));
  recordedBySelect: ElementFinder = element(by.css('select#answer-recordedBy'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setAnswerInput(answer) {
    await this.answerInput.sendKeys(answer);
  }

  async getAnswerInput() {
    return this.answerInput.getAttribute('value');
  }

  async setDescriptionInput(description) {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput() {
    return this.descriptionInput.getAttribute('value');
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

  async setTenantIdInput(tenantId) {
    await this.tenantIdInput.sendKeys(tenantId);
  }

  async getTenantIdInput() {
    return this.tenantIdInput.getAttribute('value');
  }

  async questionSelectLastOption() {
    await this.questionSelect.all(by.tagName('option')).last().click();
  }

  async questionSelectOption(option) {
    await this.questionSelect.sendKeys(option);
  }

  getQuestionSelect() {
    return this.questionSelect;
  }

  async getQuestionSelectedOption() {
    return this.questionSelect.element(by.css('option:checked')).getText();
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

  async recordedBySelectLastOption() {
    await this.recordedBySelect.all(by.tagName('option')).last().click();
  }

  async recordedBySelectOption(option) {
    await this.recordedBySelect.sendKeys(option);
  }

  getRecordedBySelect() {
    return this.recordedBySelect;
  }

  async getRecordedBySelectedOption() {
    return this.recordedBySelect.element(by.css('option:checked')).getText();
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
    await this.setAnswerInput('answer');
    expect(await this.getAnswerInput()).to.match(/answer/);
    await waitUntilDisplayed(this.saveButton);
    await this.setDescriptionInput('description');
    expect(await this.getDescriptionInput()).to.match(/description/);
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
    await this.setCreatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getCreatedDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setLastUpdatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getLastUpdatedDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setTenantIdInput('5');
    expect(await this.getTenantIdInput()).to.eq('5');
    await this.questionSelectLastOption();
    await this.serviceUserSelectLastOption();
    await this.recordedBySelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
