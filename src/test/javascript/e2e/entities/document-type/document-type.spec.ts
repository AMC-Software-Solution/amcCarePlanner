import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import DocumentTypeComponentsPage from './document-type.page-object';
import DocumentTypeUpdatePage from './document-type-update.page-object';
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

describe('DocumentType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let documentTypeComponentsPage: DocumentTypeComponentsPage;
  let documentTypeUpdatePage: DocumentTypeUpdatePage;

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
    documentTypeComponentsPage = new DocumentTypeComponentsPage();
    documentTypeComponentsPage = await documentTypeComponentsPage.goToPage(navBarPage);
  });

  it('should load DocumentTypes', async () => {
    expect(await documentTypeComponentsPage.title.getText()).to.match(/Document Types/);
    expect(await documentTypeComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete DocumentTypes', async () => {
    const beforeRecordsCount = (await isVisible(documentTypeComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(documentTypeComponentsPage.table);
    documentTypeUpdatePage = await documentTypeComponentsPage.goToCreateDocumentType();
    await documentTypeUpdatePage.enterData();

    expect(await documentTypeComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(documentTypeComponentsPage.table);
    await waitUntilCount(documentTypeComponentsPage.records, beforeRecordsCount + 1);
    expect(await documentTypeComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await documentTypeComponentsPage.deleteDocumentType();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(documentTypeComponentsPage.records, beforeRecordsCount);
      expect(await documentTypeComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(documentTypeComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
