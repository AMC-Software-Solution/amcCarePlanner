import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class DisabilityTypeUpdatePage {
  pageTitle: ElementFinder = element(by.id('carePlannerApp.disabilityType.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  disabilityInput: ElementFinder = element(by.css('input#disability-type-disability'));
  descriptionInput: ElementFinder = element(by.css('input#disability-type-description'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setDisabilityInput(disability) {
    await this.disabilityInput.sendKeys(disability);
  }

  async getDisabilityInput() {
    return this.disabilityInput.getAttribute('value');
  }

  async setDescriptionInput(description) {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput() {
    return this.descriptionInput.getAttribute('value');
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
    await this.setDisabilityInput('disability');
    expect(await this.getDisabilityInput()).to.match(/disability/);
    await waitUntilDisplayed(this.saveButton);
    await this.setDescriptionInput('description');
    expect(await this.getDescriptionInput()).to.match(/description/);
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
