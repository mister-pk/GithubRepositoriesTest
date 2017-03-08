package com.kolpakov.githubrepositories.data.models;

import retrofit2.Response;

/**
 * Created by pkolpakov on 06.03.2017.
 */

public class PageLinks {

    private String HEADER_LINK = "Link";
    private String HEADER_NEXT = "X-Next";
    private String HEADER_LAST = "X-Last";
    private String META_REL = "rel";
    private String META_LAST = "last";
    private String META_NEXT = "next";
    private String META_FIRST = "first";
    private String META_PREV = "prev";


    private static final String DELIM_LINKS = ",";

    private static final String DELIM_LINK_PARAM = ";";

    private String first;
    private String last;
    private String next;
    private String prev;
    /**
     * Parse links from executed method
     *
     * @param response
     */
    public PageLinks(Response response) {
        String linkHeader = response.headers().get(HEADER_LINK);
        if (linkHeader != null) {
            String[] links = linkHeader.split(DELIM_LINKS);
            for (String link : links) {
                String[] segments = link.split(DELIM_LINK_PARAM);
                if (segments.length < 2)
                    continue;

                String linkPart = segments[0].trim();
                if (!linkPart.startsWith("<") || !linkPart.endsWith(">")) //$NON-NLS-1$ //$NON-NLS-2$
                    continue;
                linkPart = linkPart.substring(1, linkPart.length() - 1);

                for (int i = 1; i < segments.length; i++) {
                    String[] rel = segments[i].trim().split("="); //$NON-NLS-1$
                    if (rel.length < 2 || !META_REL.equals(rel[0]))
                        continue;

                    String relValue = rel[1];
                    if (relValue.startsWith("\"") && relValue.endsWith("\"")) //$NON-NLS-1$ //$NON-NLS-2$
                        relValue = relValue.substring(1, relValue.length() - 1);

                    if (META_FIRST.equals(relValue))
                        first = linkPart;
                    else if (META_LAST.equals(relValue))
                        last = linkPart;
                    else if (META_NEXT.equals(relValue))
                        next = linkPart;
                    else if (META_PREV.equals(relValue))
                        prev = linkPart;
                }
            }
        } else {
            next = response.headers().get(HEADER_NEXT);
            last =  response.headers().get(HEADER_LAST);
        }
    }

    public String getFirst() {
        return first;
    }

    public String getLast() {
        return last;
    }

    public String getNext() {
        return next;
    }

    public String getPrev() {
        return prev;
    }
}