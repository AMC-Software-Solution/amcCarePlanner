import { element, by, ElementFinder, protractor } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class EmergencyContactUpdatePage {
  pageTitle: ElementFinder = element(by.id('carePlannerApp.emergencyContact.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  nameInput: ElementFinder = element(by.css('input#emergency-contact-name'));
  contactRelationshipInput: ElementFinder = element(by.css('input#emergency-contact-contactRelationship'));
  isKeyHolderInput: ElementFinder = element(by.css('input#emergency-contact-isKeyHolder'));
  infoSharingConsentGivenInput: ElementFinder = element(by.css('input#emergency-contact-infoSharingConsentGiven'));
  preferredContactNumberInput: ElementFinder = element(by.css('input#emergency-contact-preferredContactNumber'));
  fullAddressInput: ElementFinder = element(by.css('input#emergency-contact-fullAddress'));
  lastUpdatedDateInput: ElementFinder = element(by.css('input#emergency-contact-lastUpdatedDate'));
  tenantIdInput: ElementFinder = element(by.css('input#emergency-contact-tenantId'));
  serviceUserSelect: ElementFinder = element(by.css('select#emergency-contact-serviceUser'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return this.nameInput.getAttribute('value');
  }

  async setContactRelationshipInput(contactRelationship) {
    await this.contactRelationshipInput.sendKeys(contactRelationship);
  }

  async getContactRelationshipInput() {
    return this.contactRelationshipInput.getAttribute('value');
  }

  getIsKeyHolderInput() {
    return this.isKeyHolderInput;
  }
  getInfoSharingConsentGivenInput() {
    return this.infoSharingConsentGivenInput;
  }
  async setPreferredContactNumberInput(preferredContactNumber) {
    await this.preferredContactNumberInput.sendKeys(preferredContactNumber);
  }

  async getPreferredContactNumberInput() {
    return this.preferredContactNumberInput.getAttribute('value');
  }

  async setFullAddressInput(fullAddress) {
    await this.fullAddressInput.sendKeys(fullAddress);
  }

  async getFullAddressInput() {
    return this.fullAddressInput.getAttribute('value');
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
    await this.setNameInput('name');
    expect(await this.getNameInput()).to.match(/name/);
    await waitUntilDisplayed(this.saveButton);
    await this.setContactRelationshipInput('contactRelationship');
    expect(await this.getContactRelationshipInput()).to.match(/contactRelationship/);
    await waitUntilDisplayed(this.saveButton);
    const selectedIsKeyHolder = await this.getIsKeyHolderInput().isSelected();
    if (selectedIsKeyHolder) {
      await this.getIsKeyHolderInput().click();
      expect(await this.getIsKeyHolderInput().isSelected()).to.be.false;
    } else {
      await this.getIsKeyHolderInput().click();
      expect(await this.getIsKeyHolderInput().isSelected()).to.be.true;
    }
    await waitUntilDisplayed(this.saveButton);
    const selectedInfoSharingConsentGiven = await this.getInfoSharingConsentGivenInput().isSelected();
    if (selectedInfoSharingConsentGiven) {
      await this.getInfoSharingConsentGivenInput().click();
      expect(await this.getInfoSharingConsentGivenInput().isSelected()).to.be.false;
    } else {
      await this.getInfoSharingConsentGivenInput().click();
      expect(await this.getInfoSharingConsentGivenInput().isSelected()).to.be.true;
    }
    await waitUntilDisplayed(this.saveButton);
    await this.setPreferredContactNumberInput('preferredContactNumber');
    expect(await this.getPreferredContactNumberInput()).to.match(/preferredContactNumber/);
    await waitUntilDisplayed(this.saveButton);
    await this.setFullAddressInput('fullAddress');
    expect(await this.getFullAddressInput()).to.match(/fullAddress/);
    await waitUntilDisplayed(this.saveButton);
    await this.setLastUpdatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getLastUpdatedDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setTenantIdInput('5');
    expect(await this.getTenantIdInput()).to.eq('5');
    await this.serviceUserSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
