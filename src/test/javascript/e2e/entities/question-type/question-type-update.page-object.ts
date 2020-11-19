import { element, by, ElementFinder, protractor } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class QuestionTypeUpdatePage {
  pageTitle: ElementFinder = element(by.id('carePlannerApp.questionType.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  questionTypeInput: ElementFinder = element(by.css('input#question-type-questionType'));
  lastUpdatedDateInput: ElementFinder = element(by.css('input#question-type-lastUpdatedDate'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setQuestionTypeInput(questionType) {
    await this.questionTypeInput.sendKeys(questionType);
  }

  async getQuestionTypeInput() {
    return this.questionTypeInput.getAttribute('value');
  }

  async setLastUpdatedDateInput(lastUpdatedDate) {
    await this.lastUpdatedDateInput.sendKeys(lastUpdatedDate);
  }

  async getLastUpdatedDateInput() {
    return this.lastUpdatedDateInput.getAttribute('value');
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
    await this.setQuestionTypeInput('questionType');
    expect(await this.getQuestionTypeInput()).to.match(/questionType/);
    await waitUntilDisplayed(this.saveButton);
    await this.setLastUpdatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getLastUpdatedDateInput()).to.contain('2001-01-01T02:30');
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
