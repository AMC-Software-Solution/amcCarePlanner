import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ServiceUserContactComponentsPage from './service-user-contact.page-object';
import ServiceUserContactUpdatePage from './service-user-contact-update.page-object';
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

describe('ServiceUserContact e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let serviceUserContactComponentsPage: ServiceUserContactComponentsPage;
  let serviceUserContactUpdatePage: ServiceUserContactUpdatePage;

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
    serviceUserContactComponentsPage = new ServiceUserContactComponentsPage();
    serviceUserContactComponentsPage = await serviceUserContactComponentsPage.goToPage(navBarPage);
  });

  it('should load ServiceUserContacts', async () => {
    expect(await serviceUserContactComponentsPage.title.getText()).to.match(/Service User Contacts/);
    expect(await serviceUserContactComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete ServiceUserContacts', async () => {
    const beforeRecordsCount = (await isVisible(serviceUserContactComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(serviceUserContactComponentsPage.table);
    serviceUserContactUpdatePage = await serviceUserContactComponentsPage.goToCreateServiceUserContact();
    await serviceUserContactUpdatePage.enterData();

    expect(await serviceUserContactComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(serviceUserContactComponentsPage.table);
    await waitUntilCount(serviceUserContactComponentsPage.records, beforeRecordsCount + 1);
    expect(await serviceUserContactComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await serviceUserContactComponentsPage.deleteServiceUserContact();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(serviceUserContactComponentsPage.records, beforeRecordsCount);
      expect(await serviceUserContactComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(serviceUserContactComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
