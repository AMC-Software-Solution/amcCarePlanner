import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class EligibilityTypeUpdatePage {
  pageTitle: ElementFinder = element(by.id('carePlannerApp.eligibilityType.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  eligibilityTypeInput: ElementFinder = element(by.css('input#eligibility-type-eligibilityType'));
  descriptionInput: ElementFinder = element(by.css('input#eligibility-type-description'));
  hasExtraDataInput: ElementFinder = element(by.css('input#eligibility-type-hasExtraData'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setEligibilityTypeInput(eligibilityType) {
    await this.eligibilityTypeInput.sendKeys(eligibilityType);
  }

  async getEligibilityTypeInput() {
    return this.eligibilityTypeInput.getAttribute('value');
  }

  async setDescriptionInput(description) {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput() {
    return this.descriptionInput.getAttribute('value');
  }

  getHasExtraDataInput() {
    return this.hasExtraDataInput;
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
    await this.setEligibilityTypeInput('eligibilityType');
    expect(await this.getEligibilityTypeInput()).to.match(/eligibilityType/);
    await waitUntilDisplayed(this.saveButton);
    await this.setDescriptionInput('description');
    expect(await this.getDescriptionInput()).to.match(/description/);
    await waitUntilDisplayed(this.saveButton);
    const selectedHasExtraData = await this.getHasExtraDataInput().isSelected();
    if (selectedHasExtraData) {
      await this.getHasExtraDataInput().click();
      expect(await this.getHasExtraDataInput().isSelected()).to.be.false;
    } else {
      await this.getHasExtraDataInput().click();
      expect(await this.getHasExtraDataInput().isSelected()).to.be.true;
    }
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
