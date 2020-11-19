import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './emergency-contact.reducer';
import { IEmergencyContact } from 'app/shared/model/emergency-contact.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IEmergencyContactProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const EmergencyContact = (props: IEmergencyContactProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );

  const getAllEntities = () => {
    props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get('sort');
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const { emergencyContactList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="emergency-contact-heading">
        <Translate contentKey="carePlannerApp.emergencyContact.home.title">Emergency Contacts</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="carePlannerApp.emergencyContact.home.createLabel">Create new Emergency Contact</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {emergencyContactList && emergencyContactList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('name')}>
                  <Translate contentKey="carePlannerApp.emergencyContact.name">Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('contactRelationship')}>
                  <Translate contentKey="carePlannerApp.emergencyContact.contactRelationship">Contact Relationship</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isKeyHolder')}>
                  <Translate contentKey="carePlannerApp.emergencyContact.isKeyHolder">Is Key Holder</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('infoSharingConsentGiven')}>
                  <Translate contentKey="carePlannerApp.emergencyContact.infoSharingConsentGiven">Info Sharing Consent Given</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('preferredContactNumber')}>
                  <Translate contentKey="carePlannerApp.emergencyContact.preferredContactNumber">Preferred Contact Number</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fullAddress')}>
                  <Translate contentKey="carePlannerApp.emergencyContact.fullAddress">Full Address</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lastUpdatedDate')}>
                  <Translate contentKey="carePlannerApp.emergencyContact.lastUpdatedDate">Last Updated Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('tenantId')}>
                  <Translate contentKey="carePlannerApp.emergencyContact.tenantId">Tenant Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="carePlannerApp.emergencyContact.serviceUser">Service User</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {emergencyContactList.map((emergencyContact, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${emergencyContact.id}`} color="link" size="sm">
                      {emergencyContact.id}
                    </Button>
                  </td>
                  <td>{emergencyContact.name}</td>
                  <td>{emergencyContact.contactRelationship}</td>
                  <td>{emergencyContact.isKeyHolder ? 'true' : 'false'}</td>
                  <td>{emergencyContact.infoSharingConsentGiven ? 'true' : 'false'}</td>
                  <td>{emergencyContact.preferredContactNumber}</td>
                  <td>{emergencyContact.fullAddress}</td>
                  <td>
                    {emergencyContact.lastUpdatedDate ? (
                      <TextFormat type="date" value={emergencyContact.lastUpdatedDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{emergencyContact.tenantId}</td>
                  <td>
                    {emergencyContact.serviceUserServiceUserCode ? (
                      <Link to={`service-user/${emergencyContact.serviceUserId}`}>{emergencyContact.serviceUserServiceUserCode}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${emergencyContact.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${emergencyContact.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${emergencyContact.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="carePlannerApp.emergencyContact.home.notFound">No Emergency Contacts found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={emergencyContactList && emergencyContactList.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </Row>
          <Row className="justify-content-center">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={props.totalItems}
            />
          </Row>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

const mapStateToProps = ({ emergencyContact }: IRootState) => ({
  emergencyContactList: emergencyContact.entities,
  loading: emergencyContact.loading,
  totalItems: emergencyContact.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmergencyContact);
