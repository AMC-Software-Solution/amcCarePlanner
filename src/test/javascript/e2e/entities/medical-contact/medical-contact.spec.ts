import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import MedicalContactComponentsPage from './medical-contact.page-object';
import MedicalContactUpdatePage from './medical-contact-update.page-object';
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

describe('MedicalContact e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let medicalContactComponentsPage: MedicalContactComponentsPage;
  let medicalContactUpdatePage: MedicalContactUpdatePage;

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
    medicalContactComponentsPage = new MedicalContactComponentsPage();
    medicalContactComponentsPage = await medicalContactComponentsPage.goToPage(navBarPage);
  });

  it('should load MedicalContacts', async () => {
    expect(await medicalContactComponentsPage.title.getText()).to.match(/Medical Contacts/);
    expect(await medicalContactComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete MedicalContacts', async () => {
    const beforeRecordsCount = (await isVisible(medicalContactComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(medicalContactComponentsPage.table);
    medicalContactUpdatePage = await medicalContactComponentsPage.goToCreateMedicalContact();
    await medicalContactUpdatePage.enterData();

    expect(await medicalContactComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(medicalContactComponentsPage.table);
    await waitUntilCount(medicalContactComponentsPage.records, beforeRecordsCount + 1);
    expect(await medicalContactComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await medicalContactComponentsPage.deleteMedicalContact();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(medicalContactComponentsPage.records, beforeRecordsCount);
      expect(await medicalContactComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(medicalContactComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
