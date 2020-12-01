import { element, by, ElementFinder, protractor } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class TravelUpdatePage {
  pageTitle: ElementFinder = element(by.id('carePlannerApp.travel.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  travelModeSelect: ElementFinder = element(by.css('select#travel-travelMode'));
  distanceToDestinationInput: ElementFinder = element(by.css('input#travel-distanceToDestination'));
  timeToDestinationInput: ElementFinder = element(by.css('input#travel-timeToDestination'));
  actualDistanceRequiredInput: ElementFinder = element(by.css('input#travel-actualDistanceRequired'));
  actualTimeRequiredInput: ElementFinder = element(by.css('input#travel-actualTimeRequired'));
  travelStatusSelect: ElementFinder = element(by.css('select#travel-travelStatus'));
  lastUpdatedDateInput: ElementFinder = element(by.css('input#travel-lastUpdatedDate'));
  clientIdInput: ElementFinder = element(by.css('input#travel-clientId'));
  taskSelect: ElementFinder = element(by.css('select#travel-task'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setTravelModeSelect(travelMode) {
    await this.travelModeSelect.sendKeys(travelMode);
  }

  async getTravelModeSelect() {
    return this.travelModeSelect.element(by.css('option:checked')).getText();
  }

  async travelModeSelectLastOption() {
    await this.travelModeSelect.all(by.tagName('option')).last().click();
  }
  async setDistanceToDestinationInput(distanceToDestination) {
    await this.distanceToDestinationInput.sendKeys(distanceToDestination);
  }

  async getDistanceToDestinationInput() {
    return this.distanceToDestinationInput.getAttribute('value');
  }

  async setTimeToDestinationInput(timeToDestination) {
    await this.timeToDestinationInput.sendKeys(timeToDestination);
  }

  async getTimeToDestinationInput() {
    return this.timeToDestinationInput.getAttribute('value');
  }

  async setActualDistanceRequiredInput(actualDistanceRequired) {
    await this.actualDistanceRequiredInput.sendKeys(actualDistanceRequired);
  }

  async getActualDistanceRequiredInput() {
    return this.actualDistanceRequiredInput.getAttribute('value');
  }

  async setActualTimeRequiredInput(actualTimeRequired) {
    await this.actualTimeRequiredInput.sendKeys(actualTimeRequired);
  }

  async getActualTimeRequiredInput() {
    return this.actualTimeRequiredInput.getAttribute('value');
  }

  async setTravelStatusSelect(travelStatus) {
    await this.travelStatusSelect.sendKeys(travelStatus);
  }

  async getTravelStatusSelect() {
    return this.travelStatusSelect.element(by.css('option:checked')).getText();
  }

  async travelStatusSelectLastOption() {
    await this.travelStatusSelect.all(by.tagName('option')).last().click();
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

  async taskSelectLastOption() {
    await this.taskSelect.all(by.tagName('option')).last().click();
  }

  async taskSelectOption(option) {
    await this.taskSelect.sendKeys(option);
  }

  getTaskSelect() {
    return this.taskSelect;
  }

  async getTaskSelectedOption() {
    return this.taskSelect.element(by.css('option:checked')).getText();
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
    await this.travelModeSelectLastOption();
    await waitUntilDisplayed(this.saveButton);
    await this.setDistanceToDestinationInput('5');
    expect(await this.getDistanceToDestinationInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setTimeToDestinationInput('5');
    expect(await this.getTimeToDestinationInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setActualDistanceRequiredInput('5');
    expect(await this.getActualDistanceRequiredInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setActualTimeRequiredInput('5');
    expect(await this.getActualTimeRequiredInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.travelStatusSelectLastOption();
    await waitUntilDisplayed(this.saveButton);
    await this.setLastUpdatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getLastUpdatedDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setClientIdInput('5');
    expect(await this.getClientIdInput()).to.eq('5');
    await this.taskSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
