import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import TerminalDeviceComponentsPage from './terminal-device.page-object';
import TerminalDeviceUpdatePage from './terminal-device-update.page-object';
import {
  waitUntilDisplayed,
  waitUntilAnyDisplayed,
  click,
  getRecordsCount,
  waitUntilHidden,
  waitUntilCount,
  isVisible,
} from '../../util/utils';

const expect = chai.expect;

describe('TerminalDevice e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let terminalDeviceComponentsPage: TerminalDeviceComponentsPage;
  let terminalDeviceUpdatePage: TerminalDeviceUpdatePage;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.waitUntilDisplayed();

    await signInPage.username.sendKeys('admin');
    await signInPage.password.sendKeys('admin');
    await signInPage.loginButton.click();
    await signInPage.waitUntilHidden();
    await waitUntilDisplayed(navBarPage.entityMenu);
    await waitUntilDisplayed(navBarPage.adminMenu);
    await waitUntilDisplayed(navBarPage.accountMenu);
  });

  beforeEach(async () => {
    await browser.get('/');
    await waitUntilDisplayed(navBarPage.entityMenu);
    terminalDeviceComponentsPage = new TerminalDeviceComponentsPage();
    terminalDeviceComponentsPage = await terminalDeviceComponentsPage.goToPage(navBarPage);
  });

  it('should load TerminalDevices', async () => {
    expect(await terminalDeviceComponentsPage.title.getText()).to.match(/Terminal Devices/);
    expect(await terminalDeviceComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete TerminalDevices', async () => {
    const beforeRecordsCount = (await isVisible(terminalDeviceComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(terminalDeviceComponentsPage.table);
    terminalDeviceUpdatePage = await terminalDeviceComponentsPage.goToCreateTerminalDevice();
    await terminalDeviceUpdatePage.enterData();

    expect(await terminalDeviceComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(terminalDeviceComponentsPage.table);
    await waitUntilCount(terminalDeviceComponentsPage.records, beforeRecordsCount + 1);
    expect(await terminalDeviceComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await terminalDeviceComponentsPage.deleteTerminalDevice();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(terminalDeviceComponentsPage.records, beforeRecordsCount);
      expect(await terminalDeviceComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(terminalDeviceComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
