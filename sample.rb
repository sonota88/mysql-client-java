#!/usr/bin/env ruby

require 'json'
require 'pp'

require 'bundler/inline'

gemfile do
  source 'https://rubygems.org'
  gem 'mrtable'
end

out = `./run.sh config_foo.properties sample_foo.sql`

head_cols = nil
rows = []

out.each_line{|line|
  cols = JSON.parse(line)
  if head_cols.nil?
    head_cols = cols
  else
    rows << cols
  end
}

puts Mrtable.generate(head_cols, rows)
