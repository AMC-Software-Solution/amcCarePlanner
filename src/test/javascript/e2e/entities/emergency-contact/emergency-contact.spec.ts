import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import EmergencyContactComponentsPage from './emergency-contact.page-object';
import EmergencyContactUpdatePage from './emergency-contact-update.page-object';
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

describe('EmergencyContact e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let emergencyContactComponentsPage: EmergencyContactComponentsPage;
  let emergencyContactUpdatePage: EmergencyContactUpdatePage;

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
    emergencyContactComponentsPage = new EmergencyContactComponentsPage();
    emergencyContactComponentsPage = await emergencyContactComponentsPage.goToPage(navBarPage);
  });

  it('should load EmergencyContacts', async () => {
    expect(await emergencyContactComponentsPage.title.getText()).to.match(/Emergency Contacts/);
    expect(await emergencyContactComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete EmergencyContacts', async () => {
    const beforeRecordsCount = (await isVisible(emergencyContactComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(emergencyContactComponentsPage.table);
    emergencyContactUpdatePage = await emergencyContactComponentsPage.goToCreateEmergencyContact();
    await emergencyContactUpdatePage.enterData();

    expect(await emergencyContactComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(emergencyContactComponentsPage.table);
    await waitUntilCount(emergencyContactComponentsPage.records, beforeRecordsCount + 1);
    expect(await emergencyContactComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await emergencyContactComponentsPage.deleteEmergencyContact();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(emergencyContactComponentsPage.records, beforeRecordsCount);
      expect(await emergencyContactComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(emergencyContactComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
