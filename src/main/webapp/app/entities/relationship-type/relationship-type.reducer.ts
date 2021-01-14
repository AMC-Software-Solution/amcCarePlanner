import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IRelationshipType, defaultValue } from 'app/shared/model/relationship-type.model';

export const ACTION_TYPES = {
  FETCH_RELATIONSHIPTYPE_LIST: 'relationshipType/FETCH_RELATIONSHIPTYPE_LIST',
  FETCH_RELATIONSHIPTYPE: 'relationshipType/FETCH_RELATIONSHIPTYPE',
  CREATE_RELATIONSHIPTYPE: 'relationshipType/CREATE_RELATIONSHIPTYPE',
  UPDATE_RELATIONSHIPTYPE: 'relationshipType/UPDATE_RELATIONSHIPTYPE',
  DELETE_RELATIONSHIPTYPE: 'relationshipType/DELETE_RELATIONSHIPTYPE',
  RESET: 'relationshipType/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IRelationshipType>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type RelationshipTypeState = Readonly<typeof initialState>;

// Reducer

export default (state: RelationshipTypeState = initialState, action): RelationshipTypeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_RELATIONSHIPTYPE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_RELATIONSHIPTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_RELATIONSHIPTYPE):
    case REQUEST(ACTION_TYPES.UPDATE_RELATIONSHIPTYPE):
    case REQUEST(ACTION_TYPES.DELETE_RELATIONSHIPTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_RELATIONSHIPTYPE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_RELATIONSHIPTYPE):
    case FAILURE(ACTION_TYPES.CREATE_RELATIONSHIPTYPE):
    case FAILURE(ACTION_TYPES.UPDATE_RELATIONSHIPTYPE):
    case FAILURE(ACTION_TYPES.DELETE_RELATIONSHIPTYPE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_RELATIONSHIPTYPE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_RELATIONSHIPTYPE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_RELATIONSHIPTYPE):
    case SUCCESS(ACTION_TYPES.UPDATE_RELATIONSHIPTYPE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_RELATIONSHIPTYPE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/relationship-types';

// Actions

export const getEntities: ICrudGetAllAction<IRelationshipType> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_RELATIONSHIPTYPE_LIST,
    payload: axios.get<IRelationshipType>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IRelationshipType> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_RELATIONSHIPTYPE,
    payload: axios.get<IRelationshipType>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IRelationshipType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_RELATIONSHIPTYPE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IRelationshipType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_RELATIONSHIPTYPE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IRelationshipType> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_RELATIONSHIPTYPE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
