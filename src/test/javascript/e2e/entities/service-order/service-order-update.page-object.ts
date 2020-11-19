import { element, by, ElementFinder, protractor } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class ServiceOrderUpdatePage {
  pageTitle: ElementFinder = element(by.id('carePlannerApp.serviceOrder.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  titleInput: ElementFinder = element(by.css('input#service-order-title'));
  serviceDescriptionInput: ElementFinder = element(by.css('input#service-order-serviceDescription'));
  serviceRateInput: ElementFinder = element(by.css('input#service-order-serviceRate'));
  tenantIdInput: ElementFinder = element(by.css('input#service-order-tenantId'));
  lastUpdatedDateInput: ElementFinder = element(by.css('input#service-order-lastUpdatedDate'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setTitleInput(title) {
    await this.titleInput.sendKeys(title);
  }

  async getTitleInput() {
    return this.titleInput.getAttribute('value');
  }

  async setServiceDescriptionInput(serviceDescription) {
    await this.serviceDescriptionInput.sendKeys(serviceDescription);
  }

  async getServiceDescriptionInput() {
    return this.serviceDescriptionInput.getAttribute('value');
  }

  async setServiceRateInput(serviceRate) {
    await this.serviceRateInput.sendKeys(serviceRate);
  }

  async getServiceRateInput() {
    return this.serviceRateInput.getAttribute('value');
  }

  async setTenantIdInput(tenantId) {
    await this.tenantIdInput.sendKeys(tenantId);
  }

  async getTenantIdInput() {
    return this.tenantIdInput.getAttribute('value');
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
    await this.setTitleInput('title');
    expect(await this.getTitleInput()).to.match(/title/);
    await waitUntilDisplayed(this.saveButton);
    await this.setServiceDescriptionInput('serviceDescription');
    expect(await this.getServiceDescriptionInput()).to.match(/serviceDescription/);
    await waitUntilDisplayed(this.saveButton);
    await this.setServiceRateInput('5');
    expect(await this.getServiceRateInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setTenantIdInput('5');
    expect(await this.getTenantIdInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setLastUpdatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getLastUpdatedDateInput()).to.contain('2001-01-01T02:30');
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
