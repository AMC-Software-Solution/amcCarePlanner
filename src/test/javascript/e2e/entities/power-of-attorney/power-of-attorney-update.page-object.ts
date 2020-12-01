import { element, by, ElementFinder, protractor } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class PowerOfAttorneyUpdatePage {
  pageTitle: ElementFinder = element(by.id('carePlannerApp.powerOfAttorney.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  powerOfAttorneyConsentInput: ElementFinder = element(by.css('input#power-of-attorney-powerOfAttorneyConsent'));
  healthAndWelfareInput: ElementFinder = element(by.css('input#power-of-attorney-healthAndWelfare'));
  healthAndWelfareNameInput: ElementFinder = element(by.css('input#power-of-attorney-healthAndWelfareName'));
  propertyAndFinAffairsInput: ElementFinder = element(by.css('input#power-of-attorney-propertyAndFinAffairs'));
  propertyAndFinAffairsNameInput: ElementFinder = element(by.css('input#power-of-attorney-propertyAndFinAffairsName'));
  lastUpdatedDateInput: ElementFinder = element(by.css('input#power-of-attorney-lastUpdatedDate'));
  clientIdInput: ElementFinder = element(by.css('input#power-of-attorney-clientId'));
  serviceUserSelect: ElementFinder = element(by.css('select#power-of-attorney-serviceUser'));
  witnessedBySelect: ElementFinder = element(by.css('select#power-of-attorney-witnessedBy'));

  getPageTitle() {
    return this.pageTitle;
  }

  getPowerOfAttorneyConsentInput() {
    return this.powerOfAttorneyConsentInput;
  }
  getHealthAndWelfareInput() {
    return this.healthAndWelfareInput;
  }
  async setHealthAndWelfareNameInput(healthAndWelfareName) {
    await this.healthAndWelfareNameInput.sendKeys(healthAndWelfareName);
  }

  async getHealthAndWelfareNameInput() {
    return this.healthAndWelfareNameInput.getAttribute('value');
  }

  getPropertyAndFinAffairsInput() {
    return this.propertyAndFinAffairsInput;
  }
  async setPropertyAndFinAffairsNameInput(propertyAndFinAffairsName) {
    await this.propertyAndFinAffairsNameInput.sendKeys(propertyAndFinAffairsName);
  }

  async getPropertyAndFinAffairsNameInput() {
    return this.propertyAndFinAffairsNameInput.getAttribute('value');
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

  async witnessedBySelectLastOption() {
    await this.witnessedBySelect.all(by.tagName('option')).last().click();
  }

  async witnessedBySelectOption(option) {
    await this.witnessedBySelect.sendKeys(option);
  }

  getWitnessedBySelect() {
    return this.witnessedBySelect;
  }

  async getWitnessedBySelectedOption() {
    return this.witnessedBySelect.element(by.css('option:checked')).getText();
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
    const selectedPowerOfAttorneyConsent = await this.getPowerOfAttorneyConsentInput().isSelected();
    if (selectedPowerOfAttorneyConsent) {
      await this.getPowerOfAttorneyConsentInput().click();
      expect(await this.getPowerOfAttorneyConsentInput().isSelected()).to.be.false;
    } else {
      await this.getPowerOfAttorneyConsentInput().click();
      expect(await this.getPowerOfAttorneyConsentInput().isSelected()).to.be.true;
    }
    await waitUntilDisplayed(this.saveButton);
    const selectedHealthAndWelfare = await this.getHealthAndWelfareInput().isSelected();
    if (selectedHealthAndWelfare) {
      await this.getHealthAndWelfareInput().click();
      expect(await this.getHealthAndWelfareInput().isSelected()).to.be.false;
    } else {
      await this.getHealthAndWelfareInput().click();
      expect(await this.getHealthAndWelfareInput().isSelected()).to.be.true;
    }
    await waitUntilDisplayed(this.saveButton);
    await this.setHealthAndWelfareNameInput('healthAndWelfareName');
    expect(await this.getHealthAndWelfareNameInput()).to.match(/healthAndWelfareName/);
    await waitUntilDisplayed(this.saveButton);
    const selectedPropertyAndFinAffairs = await this.getPropertyAndFinAffairsInput().isSelected();
    if (selectedPropertyAndFinAffairs) {
      await this.getPropertyAndFinAffairsInput().click();
      expect(await this.getPropertyAndFinAffairsInput().isSelected()).to.be.false;
    } else {
      await this.getPropertyAndFinAffairsInput().click();
      expect(await this.getPropertyAndFinAffairsInput().isSelected()).to.be.true;
    }
    await waitUntilDisplayed(this.saveButton);
    await this.setPropertyAndFinAffairsNameInput('propertyAndFinAffairsName');
    expect(await this.getPropertyAndFinAffairsNameInput()).to.match(/propertyAndFinAffairsName/);
    await waitUntilDisplayed(this.saveButton);
    await this.setLastUpdatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getLastUpdatedDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setClientIdInput('5');
    expect(await this.getClientIdInput()).to.eq('5');
    await this.serviceUserSelectLastOption();
    await this.witnessedBySelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
