package com.blackboard.api.dao.impl.interfaces;

import com.blackboard.api.core.model.Transcript;

import java.util.List;
import java.util.Optional;

/**
 * The internal API for the persistence layer operations. These operations listed only pertain to the
 * Creation, Retrieval, Updating, and Deletion of Student objects.
 * <p>
 *
 * @author ChristopherLicata <chris@bizmerlin.com> Created by ChristopherLicata on 11/18/15.
 */

public interface TranscriptDao
{
    List<Transcript> findTranscriptsByStudentEmail(String studentEmail);

    Optional<Transcript> findTranscriptByTranscriptId(int transcriptId);

    Transcript updateTranscript(Transcript transcript);

    Transcript createTranscript(Transcript transcript);

    Optional<Transcript> deleteTranscript(int transcriptId);
}
