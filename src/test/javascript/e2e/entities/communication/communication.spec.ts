import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import CommunicationComponentsPage from './communication.page-object';
import CommunicationUpdatePage from './communication-update.page-object';
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

describe('Communication e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let communicationComponentsPage: CommunicationComponentsPage;
  let communicationUpdatePage: CommunicationUpdatePage;

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
    communicationComponentsPage = new CommunicationComponentsPage();
    communicationComponentsPage = await communicationComponentsPage.goToPage(navBarPage);
  });

  it('should load Communications', async () => {
    expect(await communicationComponentsPage.title.getText()).to.match(/Communications/);
    expect(await communicationComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete Communications', async () => {
    const beforeRecordsCount = (await isVisible(communicationComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(communicationComponentsPage.table);
    communicationUpdatePage = await communicationComponentsPage.goToCreateCommunication();
    await communicationUpdatePage.enterData();

    expect(await communicationComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(communicationComponentsPage.table);
    await waitUntilCount(communicationComponentsPage.records, beforeRecordsCount + 1);
    expect(await communicationComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await communicationComponentsPage.deleteCommunication();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(communicationComponentsPage.records, beforeRecordsCount);
      expect(await communicationComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(communicationComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
