import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ClientComponentsPage from './client.page-object';
import ClientUpdatePage from './client-update.page-object';
import {
  waitUntilDisplayed,
  waitUntilAnyDisplayed,
  click,
  getRecordsCount,
  waitUntilHidden,
  waitUntilCount,
  isVisible,
} from '../../util/utils';
import path from 'path';

const expect = chai.expect;

describe('Client e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let clientComponentsPage: ClientComponentsPage;
  let clientUpdatePage: ClientUpdatePage;

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
    clientComponentsPage = new ClientComponentsPage();
    clientComponentsPage = await clientComponentsPage.goToPage(navBarPage);
  });

  it('should load Clients', async () => {
    expect(await clientComponentsPage.title.getText()).to.match(/Clients/);
    expect(await clientComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete Clients', async () => {
    const beforeRecordsCount = (await isVisible(clientComponentsPage.noRecords)) ? 0 : await getRecordsCount(clientComponentsPage.table);
    clientUpdatePage = await clientComponentsPage.goToCreateClient();
    await clientUpdatePage.enterData();

    expect(await clientComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(clientComponentsPage.table);
    await waitUntilCount(clientComponentsPage.records, beforeRecordsCount + 1);
    expect(await clientComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await clientComponentsPage.deleteClient();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(clientComponentsPage.records, beforeRecordsCount);
      expect(await clientComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(clientComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
