import { element, by, ElementFinder, protractor } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class QuestionUpdatePage {
  pageTitle: ElementFinder = element(by.id('carePlannerApp.question.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  questionInput: ElementFinder = element(by.css('input#question-question'));
  descriptionInput: ElementFinder = element(by.css('input#question-description'));
  attribute1Input: ElementFinder = element(by.css('input#question-attribute1'));
  attribute2Input: ElementFinder = element(by.css('input#question-attribute2'));
  attribute3Input: ElementFinder = element(by.css('input#question-attribute3'));
  attribute4Input: ElementFinder = element(by.css('input#question-attribute4'));
  attribute5Input: ElementFinder = element(by.css('input#question-attribute5'));
  optionalInput: ElementFinder = element(by.css('input#question-optional'));
  lastUpdatedDateInput: ElementFinder = element(by.css('input#question-lastUpdatedDate'));
  clientIdInput: ElementFinder = element(by.css('input#question-clientId'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setQuestionInput(question) {
    await this.questionInput.sendKeys(question);
  }

  async getQuestionInput() {
    return this.questionInput.getAttribute('value');
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

  getOptionalInput() {
    return this.optionalInput;
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
    await this.setQuestionInput('question');
    expect(await this.getQuestionInput()).to.match(/question/);
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
    const selectedOptional = await this.getOptionalInput().isSelected();
    if (selectedOptional) {
      await this.getOptionalInput().click();
      expect(await this.getOptionalInput().isSelected()).to.be.false;
    } else {
      await this.getOptionalInput().click();
      expect(await this.getOptionalInput().isSelected()).to.be.true;
    }
    await waitUntilDisplayed(this.saveButton);
    await this.setLastUpdatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getLastUpdatedDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setClientIdInput('5');
    expect(await this.getClientIdInput()).to.eq('5');
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
