import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import TenantDocumentComponentsPage from './tenant-document.page-object';
import TenantDocumentUpdatePage from './tenant-document-update.page-object';
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

describe('TenantDocument e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let tenantDocumentComponentsPage: TenantDocumentComponentsPage;
  let tenantDocumentUpdatePage: TenantDocumentUpdatePage;

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
    tenantDocumentComponentsPage = new TenantDocumentComponentsPage();
    tenantDocumentComponentsPage = await tenantDocumentComponentsPage.goToPage(navBarPage);
  });

  it('should load TenantDocuments', async () => {
    expect(await tenantDocumentComponentsPage.title.getText()).to.match(/Tenant Documents/);
    expect(await tenantDocumentComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete TenantDocuments', async () => {
    const beforeRecordsCount = (await isVisible(tenantDocumentComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(tenantDocumentComponentsPage.table);
    tenantDocumentUpdatePage = await tenantDocumentComponentsPage.goToCreateTenantDocument();
    await tenantDocumentUpdatePage.enterData();

    expect(await tenantDocumentComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(tenantDocumentComponentsPage.table);
    await waitUntilCount(tenantDocumentComponentsPage.records, beforeRecordsCount + 1);
    expect(await tenantDocumentComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await tenantDocumentComponentsPage.deleteTenantDocument();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(tenantDocumentComponentsPage.records, beforeRecordsCount);
      expect(await tenantDocumentComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(tenantDocumentComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
