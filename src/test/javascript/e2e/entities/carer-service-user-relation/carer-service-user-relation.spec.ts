import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import CarerServiceUserRelationComponentsPage from './carer-service-user-relation.page-object';
import CarerServiceUserRelationUpdatePage from './carer-service-user-relation-update.page-object';
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

describe('CarerServiceUserRelation e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let carerServiceUserRelationComponentsPage: CarerServiceUserRelationComponentsPage;
  let carerServiceUserRelationUpdatePage: CarerServiceUserRelationUpdatePage;

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
    carerServiceUserRelationComponentsPage = new CarerServiceUserRelationComponentsPage();
    carerServiceUserRelationComponentsPage = await carerServiceUserRelationComponentsPage.goToPage(navBarPage);
  });

  it('should load CarerServiceUserRelations', async () => {
    expect(await carerServiceUserRelationComponentsPage.title.getText()).to.match(/Carer Service User Relations/);
    expect(await carerServiceUserRelationComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete CarerServiceUserRelations', async () => {
    const beforeRecordsCount = (await isVisible(carerServiceUserRelationComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(carerServiceUserRelationComponentsPage.table);
    carerServiceUserRelationUpdatePage = await carerServiceUserRelationComponentsPage.goToCreateCarerServiceUserRelation();
    await carerServiceUserRelationUpdatePage.enterData();

    expect(await carerServiceUserRelationComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(carerServiceUserRelationComponentsPage.table);
    await waitUntilCount(carerServiceUserRelationComponentsPage.records, beforeRecordsCount + 1);
    expect(await carerServiceUserRelationComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await carerServiceUserRelationComponentsPage.deleteCarerServiceUserRelation();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(carerServiceUserRelationComponentsPage.records, beforeRecordsCount);
      expect(await carerServiceUserRelationComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(carerServiceUserRelationComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
