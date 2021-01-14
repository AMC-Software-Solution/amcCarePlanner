import { element, by, ElementFinder, protractor } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class EmployeeAvailabilityUpdatePage {
  pageTitle: ElementFinder = element(by.id('carePlannerApp.employeeAvailability.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  availableForWorkInput: ElementFinder = element(by.css('input#employee-availability-availableForWork'));
  availableMondayInput: ElementFinder = element(by.css('input#employee-availability-availableMonday'));
  availableTuesdayInput: ElementFinder = element(by.css('input#employee-availability-availableTuesday'));
  availableWednesdayInput: ElementFinder = element(by.css('input#employee-availability-availableWednesday'));
  availableThursdayInput: ElementFinder = element(by.css('input#employee-availability-availableThursday'));
  availableFridayInput: ElementFinder = element(by.css('input#employee-availability-availableFriday'));
  availableSaturdayInput: ElementFinder = element(by.css('input#employee-availability-availableSaturday'));
  availableSundayInput: ElementFinder = element(by.css('input#employee-availability-availableSunday'));
  preferredShiftSelect: ElementFinder = element(by.css('select#employee-availability-preferredShift'));
  hasExtraDataInput: ElementFinder = element(by.css('input#employee-availability-hasExtraData'));
  lastUpdatedDateInput: ElementFinder = element(by.css('input#employee-availability-lastUpdatedDate'));
  clientIdInput: ElementFinder = element(by.css('input#employee-availability-clientId'));
  employeeSelect: ElementFinder = element(by.css('select#employee-availability-employee'));

  getPageTitle() {
    return this.pageTitle;
  }

  getAvailableForWorkInput() {
    return this.availableForWorkInput;
  }
  getAvailableMondayInput() {
    return this.availableMondayInput;
  }
  getAvailableTuesdayInput() {
    return this.availableTuesdayInput;
  }
  getAvailableWednesdayInput() {
    return this.availableWednesdayInput;
  }
  getAvailableThursdayInput() {
    return this.availableThursdayInput;
  }
  getAvailableFridayInput() {
    return this.availableFridayInput;
  }
  getAvailableSaturdayInput() {
    return this.availableSaturdayInput;
  }
  getAvailableSundayInput() {
    return this.availableSundayInput;
  }
  async setPreferredShiftSelect(preferredShift) {
    await this.preferredShiftSelect.sendKeys(preferredShift);
  }

  async getPreferredShiftSelect() {
    return this.preferredShiftSelect.element(by.css('option:checked')).getText();
  }

  async preferredShiftSelectLastOption() {
    await this.preferredShiftSelect.all(by.tagName('option')).last().click();
  }
  getHasExtraDataInput() {
    return this.hasExtraDataInput;
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
    const selectedAvailableForWork = await this.getAvailableForWorkInput().isSelected();
    if (selectedAvailableForWork) {
      await this.getAvailableForWorkInput().click();
      expect(await this.getAvailableForWorkInput().isSelected()).to.be.false;
    } else {
      await this.getAvailableForWorkInput().click();
      expect(await this.getAvailableForWorkInput().isSelected()).to.be.true;
    }
    await waitUntilDisplayed(this.saveButton);
    const selectedAvailableMonday = await this.getAvailableMondayInput().isSelected();
    if (selectedAvailableMonday) {
      await this.getAvailableMondayInput().click();
      expect(await this.getAvailableMondayInput().isSelected()).to.be.false;
    } else {
      await this.getAvailableMondayInput().click();
      expect(await this.getAvailableMondayInput().isSelected()).to.be.true;
    }
    await waitUntilDisplayed(this.saveButton);
    const selectedAvailableTuesday = await this.getAvailableTuesdayInput().isSelected();
    if (selectedAvailableTuesday) {
      await this.getAvailableTuesdayInput().click();
      expect(await this.getAvailableTuesdayInput().isSelected()).to.be.false;
    } else {
      await this.getAvailableTuesdayInput().click();
      expect(await this.getAvailableTuesdayInput().isSelected()).to.be.true;
    }
    await waitUntilDisplayed(this.saveButton);
    const selectedAvailableWednesday = await this.getAvailableWednesdayInput().isSelected();
    if (selectedAvailableWednesday) {
      await this.getAvailableWednesdayInput().click();
      expect(await this.getAvailableWednesdayInput().isSelected()).to.be.false;
    } else {
      await this.getAvailableWednesdayInput().click();
      expect(await this.getAvailableWednesdayInput().isSelected()).to.be.true;
    }
    await waitUntilDisplayed(this.saveButton);
    const selectedAvailableThursday = await this.getAvailableThursdayInput().isSelected();
    if (selectedAvailableThursday) {
      await this.getAvailableThursdayInput().click();
      expect(await this.getAvailableThursdayInput().isSelected()).to.be.false;
    } else {
      await this.getAvailableThursdayInput().click();
      expect(await this.getAvailableThursdayInput().isSelected()).to.be.true;
    }
    await waitUntilDisplayed(this.saveButton);
    const selectedAvailableFriday = await this.getAvailableFridayInput().isSelected();
    if (selectedAvailableFriday) {
      await this.getAvailableFridayInput().click();
      expect(await this.getAvailableFridayInput().isSelected()).to.be.false;
    } else {
      await this.getAvailableFridayInput().click();
      expect(await this.getAvailableFridayInput().isSelected()).to.be.true;
    }
    await waitUntilDisplayed(this.saveButton);
    const selectedAvailableSaturday = await this.getAvailableSaturdayInput().isSelected();
    if (selectedAvailableSaturday) {
      await this.getAvailableSaturdayInput().click();
      expect(await this.getAvailableSaturdayInput().isSelected()).to.be.false;
    } else {
      await this.getAvailableSaturdayInput().click();
      expect(await this.getAvailableSaturdayInput().isSelected()).to.be.true;
    }
    await waitUntilDisplayed(this.saveButton);
    const selectedAvailableSunday = await this.getAvailableSundayInput().isSelected();
    if (selectedAvailableSunday) {
      await this.getAvailableSundayInput().click();
      expect(await this.getAvailableSundayInput().isSelected()).to.be.false;
    } else {
      await this.getAvailableSundayInput().click();
      expect(await this.getAvailableSundayInput().isSelected()).to.be.true;
    }
    await waitUntilDisplayed(this.saveButton);
    await this.preferredShiftSelectLastOption();
    await waitUntilDisplayed(this.saveButton);
    const selectedHasExtraData = await this.getHasExtraDataInput().isSelected();
    if (selectedHasExtraData) {
      await this.getHasExtraDataInput().click();
      expect(await this.getHasExtraDataInput().isSelected()).to.be.false;
    } else {
      await this.getHasExtraDataInput().click();
      expect(await this.getHasExtraDataInput().isSelected()).to.be.true;
    }
    await waitUntilDisplayed(this.saveButton);
    await this.setLastUpdatedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getLastUpdatedDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setClientIdInput('5');
    expect(await this.getClientIdInput()).to.eq('5');
    await this.employeeSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
