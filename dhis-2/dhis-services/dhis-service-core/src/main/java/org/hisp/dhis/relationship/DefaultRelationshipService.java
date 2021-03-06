package org.hisp.dhis.relationship;

/*
 * Copyright (c) 2004-2018, University of Oslo
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * Neither the name of the HISP project nor the names of its contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import org.hisp.dhis.program.ProgramInstance;
import org.hisp.dhis.program.ProgramStageInstance;
import org.hisp.dhis.trackedentity.TrackedEntityInstance;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Abyot Asalefew
 */
@Transactional
public class DefaultRelationshipService
    implements RelationshipService
{
    // -------------------------------------------------------------------------
    // Dependencies
    // -------------------------------------------------------------------------

    private RelationshipStore relationshipStore;

    public void setRelationshipStore( RelationshipStore relationshipStore )
    {
        this.relationshipStore = relationshipStore;
    }

    // -------------------------------------------------------------------------
    // Implementation methods
    // -------------------------------------------------------------------------

    @Override
    public void deleteRelationship( Relationship relationship )
    {
        relationshipStore.delete( relationship );
    }

    @Override
    public Relationship getRelationship( int id )
    {
        return relationshipStore.get( id );
    }

    @Override
    public boolean relationshipExists( String uid )
    {
        return relationshipStore.getByUid( uid ) != null;
    }

    @Override
    public int addRelationship( Relationship relationship )
    {
        relationship.getFrom().setRelationship( relationship );
        relationship.getTo().setRelationship( relationship );
        relationshipStore.save( relationship );

        return relationship.getId();
    }

    @Override
    public void updateRelationship( Relationship relationship )
    {
        relationship.getFrom().setRelationship( relationship );
        relationship.getTo().setRelationship( relationship );
        relationshipStore.update( relationship );
    }

    @Override
    public Relationship getRelationship( String uid )
    {
        return relationshipStore.getByUid( uid );
    }

    @Override
    public List<Relationship> getRelationshipsByTrackedEntityInstance( TrackedEntityInstance tei,
        boolean skipAccessValidation )
    {
        return relationshipStore.getByTrackedEntityInstance( tei );
    }

    @Override
    public List<Relationship> getRelationshipsByProgramInstance( ProgramInstance pi, boolean skipAccessValidation )
    {
        return relationshipStore.getByProgramInstance( pi );
    }

    @Override
    public List<Relationship> getRelationshipsByProgramStageInstance( ProgramStageInstance psi,
        boolean skipAccessValidation )
    {
        return relationshipStore.getByProgramStageInstance( psi );
    }
}
