package com.github.jartisan.latest.global.entity;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

/***
 * 
 * GithubInfo
 * @author jalen
 *
 */
public class GithubInfo {
	
	/***
	 * stargazers_count
	 */
	private int starCount;
	
	/***
	 *subscribers_count 
	 */
	private int watcherCount;
	
	/***
	 * forks
	 */
	private int forkCount;
	
	/***
	 * 项目创建时间
	 */
	private Date createdDate;
	
	/***
	 * 上一次更新日期
	 */
	private Date lastUpdated;
	
	/***
	 * commits sha
	 */
	private List<String> commits;
	
	/***
	 * tags
	 */
	private List<String> tags;
	
	/***
	 * releases
	 */
	private List<String> releases;
	

	public int getStarCount() {
		return starCount;
	}

	public void setStarCount(int starCount) {
		this.starCount = starCount;
	}

	public int getWatcherCount() {
		return watcherCount;
	}

	public void setWatcherCount(int watcherCount) {
		this.watcherCount = watcherCount;
	}

	public int getForkCount() {
		return forkCount;
	}

	public void setForkCount(int forkCount) {
		this.forkCount = forkCount;
	}

	 public Date getLastUpdated() {
		 if (this.lastUpdated != null) {
				return new Date(this.lastUpdated.getTime());
			} else {
				return null;
			}
	}

	public void setLastUpdated(Date lastUpdated) {
		if (lastUpdated != null) {
			this.lastUpdated = (Date) lastUpdated.clone();
		} else {
			this.lastUpdated = null;
		}
	}

	public Date getCreatedDate() {
			if (this.createdDate != null) {
				return new Date(this.createdDate.getTime());
			} else {
				return null;
			}
		}

		public void setCreatedDate(Date createdDate) {
			if (createdDate != null) {
				this.createdDate = (Date) createdDate.clone();
			} else {
				this.createdDate = null;
			}
		}
		
		
		public List<String> getTags() {
			return tags;
		}

		public void setTags(List<String> tags) {
			this.tags = tags;
		}
		

		public List<String> getCommits() {
			return commits;
		}

		public void setCommits(List<String> commits) {
			this.commits = commits;
		}

		public List<String> getReleases() {
			return releases;
		}

		public void setReleases(List<String> releases) {
			this.releases = releases;
		}

		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this);
		}
	
}
